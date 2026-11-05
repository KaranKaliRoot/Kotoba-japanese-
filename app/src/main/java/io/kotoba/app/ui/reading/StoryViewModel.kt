package io.kotoba.app.ui.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.ReadingStory
import io.kotoba.app.domain.model.StoryProgress
import io.kotoba.app.domain.model.Vocab
import io.kotoba.app.domain.repository.StoryRepository
import io.kotoba.app.domain.repository.SettingsRepository
import io.kotoba.app.domain.repository.VocabRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StoryUiState(
    val level: JlptLevel = JlptLevel.N5,
    val stories: List<ReadingStory> = emptyList(),
    val progress: List<StoryProgress> = emptyList(),
    val selected: ReadingStory? = null,
    val showFurigana: Boolean = true,
    val showTranslation: Boolean = false,
    val tappedWord: Vocab? = null,
    val sentenceBreakdown: List<String> = emptyList()
)

class StoryViewModel(
    private val storyRepository: StoryRepository,
    private val settingsRepository: SettingsRepository,
    private val vocabRepository: VocabRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(StoryUiState())
    val ui: StateFlow<StoryUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                val level = settings.activeLevel
                _ui.update { it.copy(level = level) }
                storyRepository.getByLevel(level).collect { stories ->
                    _ui.update { it.copy(stories = stories) }
                }
                storyRepository.getProgressByLevel(level).collect { prog ->
                    _ui.update { it.copy(progress = prog) }
                }
            }
        }
    }

    fun select(story: ReadingStory) {
        _ui.update { it.copy(selected = story, showFurigana = true, showTranslation = false) }
    }

    fun back() = _ui.update { it.copy(selected = null) }

    fun toggleFurigana() = _ui.update { it.copy(showFurigana = !it.showFurigana) }

    fun toggleTranslation() = _ui.update { it.copy(showTranslation = !it.showTranslation) }

    fun tapWord(word: Vocab) = _ui.update { it.copy(tappedWord = word) }

    fun closePopup() = _ui.update { it.copy(tappedWord = null) }

    fun analyzeSentence(sentence: String) {
        viewModelScope.launch {
            vocabRepository.getByLevel(_ui.value.level).collect { list ->
                val meanings = list.filter { w -> sentence.contains(w.kanji) }.map { it.meaning }
                _ui.update { it.copy(sentenceBreakdown = meanings) }
            }
        }
    }
}