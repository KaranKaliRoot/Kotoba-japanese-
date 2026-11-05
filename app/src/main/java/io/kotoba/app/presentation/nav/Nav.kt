package io.kotoba.app.presentation.nav

enum class KotobaTab(val route: String, val label: String, val icon: String) {
    Home("home", "Home", "✦"),
    Vocab("vocab", "Vocab", "Aa"),
    Kanji("kanji", "Kanji", "漢"),
    Grammar("grammar", "Grammar", "文"),
    Reading("reading", "Reading", "読")
}

data class NavDestination(val route: String, val label: String)

val bottomTabs = KotobaTab.entries

const val KanjiDetailRoute = "kanji_detail"
const val KanjiGridRoute = "kanji"
const val GrammarDetailRoute = "grammar_detail"
const val StoryDetailRoute = "story_detail"