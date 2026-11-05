package io.kotoba.app.domain.model

data class Grammar(
    val id: Long,
    val level: JlptLevel,
    val pattern: String,
    val reading: String = "",
    val meaning: String = "",
    val explanation: String = "",
    val example: String = "",
    val exampleMeaning: String = "",
    val source: String = "",
    val week: Int = 0,
    val day: Int = 0,
    val category: String = "",
    val nuance: String = ""
)

data class GrammarProgress(
    val grammarId: Long,
    val level: JlptLevel,
    val learned: Boolean = false,
    val lastReviewedAt: Long = 0L
)