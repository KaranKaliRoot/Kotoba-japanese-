package io.kotoba.app.presentation.kanji

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Kanji
import io.kotoba.app.domain.model.KanjiCompound
import io.kotoba.app.domain.repository.KanjiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class KanjiUiState(
    val items: List<Kanji> = emptyList(),
    val compounds: List<KanjiCompound> = emptyList(),
    val query: String = "",
    val level: JlptLevel? = null,
    val selected: Kanji? = null
)

class KanjiViewModel(
    private val kanjiRepository: KanjiRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(KanjiUiState())
    val ui: StateFlow<KanjiUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            kanjiRepository.getAll().collect { all ->
                _ui.update { it.copy(items = all) }
            }
        }
    }

    fun setQuery(q: String) {
        _ui.update { it.copy(query = q) }
        viewModelScope.launch {
            kanjiRepository.search(q).let { items -> _ui.update { s -> s.copy(items = items) } }
        }
    }

    fun setLevel(level: JlptLevel?) {
        _ui.update { it.copy(level = level) }
    }

    fun select(k: Kanji) {
        _ui.update { it.copy(selected = k) }
        viewModelScope.launch {
            val comps = kanjiRepository.getCompoundsByLevel(k.character, k.level)
            _ui.update { it.copy(compounds = comps) }
        }
    }
}