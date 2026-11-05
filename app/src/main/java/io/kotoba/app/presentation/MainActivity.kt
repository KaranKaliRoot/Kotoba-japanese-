package io.kotoba.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.kotoba.app.KotobaApp
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Kanji
import io.kotoba.app.presentation.dashboard.DashboardScreen
import io.kotoba.app.presentation.dashboard.DashboardViewModel
import io.kotoba.app.ui.kanji.KanjiDetailScreen
import io.kotoba.app.ui.kanji.KanjiGridScreen
import io.kotoba.app.presentation.kanji.KanjiViewModel
import io.kotoba.app.presentation.nav.FloatingPillNav
import io.kotoba.app.presentation.nav.GrammarDetailRoute
import io.kotoba.app.presentation.nav.KanjiDetailRoute
import io.kotoba.app.presentation.nav.KanjiGridRoute
import io.kotoba.app.presentation.nav.KotobaTab
import io.kotoba.app.presentation.nav.StoryDetailRoute
import io.kotoba.app.ui.grammar.GrammarDetailScreen
import io.kotoba.app.ui.grammar.GrammarListScreen
import io.kotoba.app.ui.grammar.GrammarViewModel
import io.kotoba.app.ui.reading.StoryListScreen
import io.kotoba.app.ui.reading.StoryReaderScreen
import io.kotoba.app.ui.reading.StoryViewModel
import io.kotoba.app.ui.vocab.VocabScreen
import io.kotoba.app.ui.vocab.VocabViewModel
import io.kotoba.app.ui.theme.KotobaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotobaTheme {
                val navController = rememberNavController()
                val backStack by navController.currentBackStackEntryAsState()
                val currentRoute = backStack?.destination?.route

                Scaffold(
                    bottomBar = {
                        FloatingPillNav(currentRoute = currentRoute, onSelected = { tab ->
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                    }
                ) { padding ->
                    NavHost(
                        navController,
                        startDestination = KotobaTab.Home.route,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(KotobaTab.Home.route) {
                            val vm: DashboardViewModel =
                                KotobaApp.container.dashboardViewModelFactory.create(DashboardViewModel::class.java)
                            DashboardScreen(
                                vm = vm,
                                onNavigate = { route -> navController.navigate(route) }
                            )
                        }
                        composable(KotobaTab.Vocab.route) {
                            val vm: VocabViewModel =
                                KotobaApp.container.vocabViewModelFactory.create(VocabViewModel::class.java)
                            VocabScreen(vm = vm)
                        }
                        composable(KanjiGridRoute) {
                            val vm: KanjiViewModel =
                                KotobaApp.container.kanjiViewModelFactory.create(KanjiViewModel::class.java)
                            val state by vm.ui.collectAsState()
                            KanjiGridScreen(
                                items = state.items,
                                query = state.query,
                                level = state.level,
                                onQuery = vm::setQuery,
                                onLevel = vm::setLevel,
                                onSelected = { k -> vm.select(k); navController.navigate("$KanjiDetailRoute/${k.character}") }
                            )
                        }
                        composable(
                            route = "$KanjiDetailRoute/{character}",
                            arguments = listOf(navArgument("character") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val character = backStackEntry.arguments?.getString("character") ?: ""
                            val vm: KanjiViewModel =
                                KotobaApp.container.kanjiViewModelFactory.create(KanjiViewModel::class.java)
                            val state by vm.ui.collectAsState()
                            val kanji = state.items.firstOrNull { it.character == character }
                                ?: kanjiDummy(character)
                            KanjiDetailScreen(kanji = kanji, compounds = state.compounds)
                        }
                        composable(KotobaTab.Grammar.route) {
                            val vm: GrammarViewModel =
                                KotobaApp.container.grammarViewModelFactory.create(GrammarViewModel::class.java)
                            GrammarListScreen(vm = vm, onSelected = { g -> vm.select(g); navController.navigate("$GrammarDetailRoute/${g.id}") })
                        }
                        composable(
                            route = "$GrammarDetailRoute/{grammarId}",
                            arguments = listOf(navArgument("grammarId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val grammarId = backStackEntry.arguments?.getLong("grammarId") ?: 0L
                            val vm: GrammarViewModel =
                                KotobaApp.container.grammarViewModelFactory.create(GrammarViewModel::class.java)
                            val state by vm.ui.collectAsState()
                            val grammar = state.items.firstOrNull { it.id == grammarId }
                            if (grammar != null) {
                                GrammarDetailScreen(
                                    grammar = grammar,
                                    quizState = state.quiz,
                                    selectedAnswer = state.selectedAnswer,
                                    onAnswer = vm::answer,
                                    onBack = vm::backToList
                                )
                            }
                        }
                        composable(KotobaTab.Reading.route) {
                            val vm: StoryViewModel =
                                KotobaApp.container.storyViewModelFactory.create(StoryViewModel::class.java)
                            StoryListScreen(vm = vm, onSelected = { s -> vm.select(s); navController.navigate("$StoryDetailRoute/${s.id}") })
                        }
                        composable(
                            route = "$StoryDetailRoute/{storyId}",
                            arguments = listOf(navArgument("storyId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val storyId = backStackEntry.arguments?.getLong("storyId") ?: 0L
                            val vm: StoryViewModel =
                                KotobaApp.container.storyViewModelFactory.create(StoryViewModel::class.java)
                            val state by vm.ui.collectAsState()
                            val story = state.stories.firstOrNull { it.id == storyId }
                            if (story != null) {
                                StoryReaderScreen(
                                    story = story,
                                    showFurigana = state.showFurigana,
                                    showTranslation = state.showTranslation,
                                    tappedWord = state.tappedWord,
                                    onToggleFurigana = vm::toggleFurigana,
                                    onToggleTranslation = vm::toggleTranslation,
                                    onTapWord = vm::tapWord,
                                    onClosePopup = vm::closePopup,
                                    onBack = vm::back
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderTab(title: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        androidx.compose.material3.Text(
            title,
            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(24.dp)
        )
    }
}

private fun kanjiDummy(char: String): Kanji = Kanji(
    character = char,
    level = JlptLevel.N5,
    meaning = "Loading..."
)