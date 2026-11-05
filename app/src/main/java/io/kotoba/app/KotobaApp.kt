package io.kotoba.app

import android.app.Application
import io.kotoba.app.data.local.KotobaDatabase
import io.kotoba.app.presentation.widget.WidgetScheduler

class KotobaApp : Application() {
    companion object {
        @JvmStatic
        lateinit var container: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        WidgetScheduler.schedule(this)
    }
}

class AppContainer(private val app: Application) {
    val database: KotobaDatabase by lazy {
        KotobaDatabase.getInstance(app)
    }

    val vocabRepository by lazy { io.kotoba.app.domain.repository.VocabRepositoryImpl(database.vocabDao) }
    val kanjiRepository by lazy { io.kotoba.app.domain.repository.KanjiRepositoryImpl(database.kanjiDao, database.kanjiCompoundDao) }
    val grammarRepository by lazy { io.kotoba.app.domain.repository.GrammarRepositoryImpl(database.grammarDao) }
    val storyRepository by lazy { io.kotoba.app.domain.repository.StoryRepositoryImpl(database.storyDao) }
    val settingsRepository by lazy { io.kotoba.app.domain.repository.SettingsRepositoryImpl(database.settingsDao) }

    val dashboardUseCases by lazy {
        io.kotoba.app.domain.usecase.DashboardUseCases(vocabRepository, kanjiRepository, grammarRepository, storyRepository)
    }
    val hourlyQuoteUseCase by lazy { io.kotoba.app.domain.usecase.HourlyQuoteUseCase() }

    val dashboardViewModelFactory by lazy {
        DashboardViewModelFactory(
            dashboardUseCases,
            settingsRepository,
            vocabRepository,
            kanjiRepository,
            grammarRepository,
            storyRepository
        )
    }

    val kanjiViewModelFactory by lazy {
        KanjiViewModelFactory(kanjiRepository)
    }
    val vocabViewModelFactory by lazy {
        VocabViewModelFactory(vocabRepository, settingsRepository)
    }
    val grammarViewModelFactory by lazy {
        GrammarViewModelFactory(grammarRepository, settingsRepository)
    }
    val storyViewModelFactory by lazy {
        StoryViewModelFactory(storyRepository, settingsRepository, vocabRepository)
    }
}

class DashboardViewModelFactory(
    private val useCases: io.kotoba.app.domain.usecase.DashboardUseCases,
    private val settings: io.kotoba.app.domain.repository.SettingsRepository,
    private val vocab: io.kotoba.app.domain.repository.VocabRepository,
    private val kanji: io.kotoba.app.domain.repository.KanjiRepository,
    private val grammar: io.kotoba.app.domain.repository.GrammarRepository,
    private val story: io.kotoba.app.domain.repository.StoryRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return io.kotoba.app.presentation.dashboard.DashboardViewModel(useCases, settings, vocab, kanji, grammar, story) as T
    }
}

class KanjiViewModelFactory(
    private val kanjiRepository: io.kotoba.app.domain.repository.KanjiRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return io.kotoba.app.presentation.kanji.KanjiViewModel(kanjiRepository) as T
    }
}

class VocabViewModelFactory(
    private val vocabRepository: io.kotoba.app.domain.repository.VocabRepository,
    private val settingsRepository: io.kotoba.app.domain.repository.SettingsRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return io.kotoba.app.ui.vocab.VocabViewModel(vocabRepository, settingsRepository) as T
    }
}

class GrammarViewModelFactory(
    private val grammarRepository: io.kotoba.app.domain.repository.GrammarRepository,
    private val settingsRepository: io.kotoba.app.domain.repository.SettingsRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return io.kotoba.app.ui.grammar.GrammarViewModel(grammarRepository, settingsRepository) as T
    }
}

class StoryViewModelFactory(
    private val storyRepository: io.kotoba.app.domain.repository.StoryRepository,
    private val settingsRepository: io.kotoba.app.domain.repository.SettingsRepository,
    private val vocabRepository: io.kotoba.app.domain.repository.VocabRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return io.kotoba.app.ui.reading.StoryViewModel(storyRepository, settingsRepository, vocabRepository) as T
    }
}