package io.kotoba.app.ui.vocab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Vocab
import io.kotoba.app.domain.model.VocabProgress
import io.kotoba.app.domain.repository.SettingsRepository
import io.kotoba.app.domain.repository.VocabRepository
import io.kotoba.app.ui.vocab.Sm2Rating
import io.kotoba.app.ui.vocab.scheduleSm2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class VocabTab { SRS_FLASHCARDS, WORD_LIST }

data class VocabUiState(
    val activeTab: VocabTab = VocabTab.SRS_FLASHCARDS,
    val level: JlptLevel = JlptLevel.N5,
    val items: List<Vocab> = emptyList(),
    val due: List<VocabProgress> = emptyList(),
    val query: String = "",
    val showReading: Boolean = true,
    val showMeaning: Boolean = true,
    val currentIndex: Int = 0
)

class VocabViewModel(
    private val vocabRepository: VocabRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(VocabUiState())
    val ui: StateFlow<VocabUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                val level = settings.activeLevel
                _ui.update { it.copy(level = level) }
                vocabRepository.getByLevel(level).collect { vocab ->
                    val due = vocabRepository.getDueCards(level, System.currentTimeMillis())
                    _ui.update { s ->
                        s.copy(items = vocab, due = due, currentIndex = 0)
                    }
                }
            }
        }
    }

    fun setTab(tab: VocabTab) = _ui.update { it.copy(activeTab = tab) }

    fun setQuery(q: String) = _ui.update { it.copy(query = q) }

    fun toggleReading() = _ui.update { it.copy(showReading = !it.showReading) }

    fun toggleMeaning() = _ui.update { it.copy(showMeaning = !it.showMeaning) }

    fun nextCard() {
        val s = _ui.value
        val next = (s.currentIndex + 1).coerceAtMost(s.due.size - 1)
        _ui.update { it.copy(currentIndex = next) }
    }

    fun rateCard(rating: Sm2Rating) {
        val s = _ui.value
        val progress = s.due.getOrNull(s.currentIndex) ?: return
        val vocab = s.items.firstOrNull { it.id == progress.vocabId } ?: return
        val result = scheduleSm2(progress, rating)
        viewModelScope.launch {
            vocabRepository.updateProgress(
                progress.copy(
                    ease = result.ease,
                    intervalDays = result.intervalDays,
                    reviewCount = result.repetition,
                    dueAt = result.dueTimestamp,
                    learned = true,
                    lastReviewedAt = System.currentTimeMillis()
                )
            )
            val fresh = vocabRepository.getDueCards(s.level, System.currentTimeMillis())
            _ui.update { it.copy(due = fresh, currentIndex = 0) }
        }
    }

    fun filteredItems(): List<Vocab> {
        val q = _ui.value.query.trim().lowercase()
        if (q.isEmpty()) return _ui.value.items
        return _ui.value.items.filter {
            it.kanji.contains(q, true) ||
                it.reading.contains(q, true) ||
                it.meaning.contains(q, true)
        }
    }
}