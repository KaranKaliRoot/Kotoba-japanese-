package io.kotoba.app.domain.model

data class UserSettings(
    val onboardingCompleted: Boolean = false,
    val dailyCardGoal: Int = 20,
    val activeLevel: JlptLevel = JlptLevel.N5,
    val theme: String = "system",
    val srsEnabled: Boolean = true,
    val showKanaGuide: Boolean = true,
    val lastStudyDay: String = "",
    val streakDays: Int = 0
)

data class StudyStats(
    val totalVocab: Int = 0,
    val learnedVocab: Int = 0,
    val totalKanji: Int = 0,
    val learnedKanji: Int = 0,
    val totalGrammar: Int = 0,
    val learnedGrammar: Int = 0,
    val dueCards: Int = 0,
    val streakDays: Int = 0
)

data class Quote(val emoji: String, val text: String)