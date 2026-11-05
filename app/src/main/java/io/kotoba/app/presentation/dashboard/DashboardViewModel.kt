package io.kotoba.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.StudyStats
import io.kotoba.app.domain.model.UserSettings
import io.kotoba.app.domain.repository.GrammarRepository
import io.kotoba.app.domain.repository.KanjiRepository
import io.kotoba.app.domain.repository.SettingsRepository
import io.kotoba.app.domain.repository.StoryRepository
import io.kotoba.app.domain.repository.VocabRepository
import io.kotoba.app.domain.usecase.DashboardUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.max

data class DashboardUiState(
    val activeLevel: JlptLevel = JlptLevel.N5,
    val settings: UserSettings = UserSettings(),
    val stats: StudyStats = StudyStats(),
    val dueCards: Int = 0,
    val totalVocab: Int = 0,
    val learnedVocab: Int = 0,
    val totalKanji: Int = 0,
    val learnedKanji: Int = 0,
    val totalGrammar: Int = 0,
    val learnedGrammar: Int = 0,
    val totalStories: Int = 0,
    val readStories: Int = 0,
    val streakDays: Int = 0,
    val dailyGoal: Int = 20,
    val progress: Float = 0f
)

class DashboardViewModel(
    private val useCases: DashboardUseCases,
    private val settingsRepository: SettingsRepository,
    private val vocabRepository: VocabRepository,
    private val kanjiRepository: KanjiRepository,
    private val grammarRepository: GrammarRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                _uiState.update {
                    it.copy(
                        settings = settings,
                        activeLevel = settings.activeLevel,
                        dailyGoal = settings.dailyCardGoal,
                        streakDays = settings.streakDays
                    )
                }
                loadLevel(settings.activeLevel)
            }
        }
    }

    private fun loadLevel(level: JlptLevel) {
        viewModelScope.launch {
            combine(
                useCases.statsForLevel(level),
                vocabRepository.countDueCards(level, System.currentTimeMillis()),
                vocabRepository.countByLevel(level),
                vocabRepository.countLearnedByLevel(level),
                kanjiRepository.countByLevel(level),
                kanjiRepository.countLearnedByLevel(level),
                grammarRepository.countByLevel(level),
                grammarRepository.countLearnedByLevel(level),
                storyRepository.countByLevel(level),
                storyRepository.getProgressByLevel(level)
            ) { values ->
                val stats = values[0] as StudyStats
                val due = values[1] as Int
                val tv = values[2] as Int
                val lv = values[3] as Int
                val tk = values[4] as Int
                val lk = values[5] as Int
                val tg = values[6] as Int
                val lg = values[7] as Int
                val ts = values[8] as Int
                val progressList = values[9] as List<*>
                val rs = progressList.size
                val goal = _uiState.value.dailyGoal
                _uiState.update {
                    it.copy(
                        activeLevel = level,
                        stats = stats,
                        dueCards = due,
                        totalVocab = tv,
                        learnedVocab = lv,
                        totalKanji = tk,
                        learnedKanji = lk,
                        totalGrammar = tg,
                        learnedGrammar = lg,
                        totalStories = ts,
                        readStories = rs,
                        progress = if (goal > 0) max(0f, lv / goal.coerceAtLeast(1).toFloat()) else 0f
                    )
                }
            }.collect {}
        }
    }

    fun setActiveLevel(level: JlptLevel) {
        viewModelScope.launch {
            settingsRepository.updateSettings(_uiState.value.settings.copy(activeLevel = level))
        }
    }
}