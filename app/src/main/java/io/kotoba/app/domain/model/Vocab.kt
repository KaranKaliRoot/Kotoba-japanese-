package io.kotoba.app.domain.model

data class Vocab(
    val id: Long,
    val level: JlptLevel,
    val kanji: String,
    val reading: String,
    val meaning: String,
    val onyomi: String = "",
    val kunyomi: String = "",
    val partOfSpeech: String = "",
    val example: String = "",
    val exampleMeaning: String = "",
    val source: String = ""
)

data class VocabProgress(
    val vocabId: Long,
    val level: JlptLevel,
    val learned: Boolean = false,
    val box: Int = 0,
    val ease: Float = 2.5f,
    val intervalDays: Int = 0,
    val dueAt: Long = 0L,
    val lastReviewedAt: Long = 0L,
    val reviewCount: Int = 0
)