package io.kotoba.app.ui.grammar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kotoba.app.domain.model.Grammar
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.repository.GrammarRepository
import io.kotoba.app.domain.repository.SettingsRepository
import io.kotoba.app.ui.grammar.GrammarMode.FUNCTIONAL
import io.kotoba.app.ui.grammar.GrammarMode.WEEKLY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class GrammarMode { WEEKLY, FUNCTIONAL }

enum class QuizState { IDLE, CORRECT, WRONG }

data class GrammarUiState(
    val mode: GrammarMode = WEEKLY,
    val level: JlptLevel = JlptLevel.N5,
    val items: List<Grammar> = emptyList(),
    val selected: Grammar? = null,
    val quiz: QuizState = QuizState.IDLE,
    val selectedAnswer: Int = -1
)

class GrammarViewModel(
    private val grammarRepository: GrammarRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(GrammarUiState())
    val ui: StateFlow<GrammarUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                val level = settings.activeLevel
                _ui.update { it.copy(level = level) }
                grammarRepository.getByLevel(level).collect { items ->
                    _ui.update { it.copy(items = items) }
                }
            }
        }
    }

    fun setMode(mode: GrammarMode) = _ui.update { it.copy(mode = mode) }

    fun select(g: Grammar) {
        _ui.update { it.copy(selected = g, quiz = QuizState.IDLE, selectedAnswer = -1) }
    }

    fun backToList() = _ui.update { it.copy(selected = null) }

    fun answer(index: Int) {
        val g = _ui.value.selected ?: return
        val correct = index == 0
        _ui.update { it.copy(selectedAnswer = index, quiz = if (correct) QuizState.CORRECT else QuizState.WRONG) }
    }

    fun groupedItems(): Map<String, List<Grammar>> {
        val s = _ui.value
        return if (s.mode == WEEKLY) {
            s.items.groupBy { "Week ${it.week}" }
        } else {
            s.items.groupBy { it.category.ifBlank { "General" } }
        }
    }
}