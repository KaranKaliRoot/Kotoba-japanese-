package io.kotoba.app.domain.model

data class Kanji(
    val character: String,
    val level: JlptLevel,
    val onyomi: String = "",
    val kunyomi: String = "",
    val meaning: String = "",
    val strokeCount: Int = 0,
    val strokeSvg: String = "",
    val jlptFrequency: Int = 0,
    val jukugo: String = "",
    val source: String = "kanjivg"
)

data class KanjiCompound(
    val character: String,
    val reading: String,
    val meaning: String,
    val level: JlptLevel
)

data class KanjiProgress(
    val character: String,
    val level: JlptLevel,
    val learned: Boolean = false,
    val strokeCount: Int = 0,
    val lastPracticedAt: Long = 0L
)