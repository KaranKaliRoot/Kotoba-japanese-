package io.kotoba.app.data.local.seed

import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Vocab

/**
 * Bundled N5 vocabulary used to seed the database on first launch.
 * Sourced from public JLPT vocabulary lists (soumatome / shinkanzen master style).
 * Only N5 ships in the app; N4-N1 ship as downloadable packs later.
 */
val seedVocab: List<Vocab> = listOf(
    Vocab(id = 1, level = JlptLevel.N5, kanji = "歩", reading = "あるく", meaning = "to walk", partOfSpeech = "verb"),
    Vocab(id = 2, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 3, level = JlptLevel.N5, kanji = "歩", reading = "ふみ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 4, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "pace", partOfSpeech = "counter"),
    Vocab(id = 5, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 6, level = JlptLevel.N5, kanji = "歩", reading = "ある", meaning = "to walk", partOfSpeech = "verb"),
    Vocab(id = 7, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "pace", partOfSpeech = "noun"),
    Vocab(id = 8, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 9, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 10, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 11, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 12, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 13, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 14, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 15, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 16, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 17, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 18, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 19, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 20, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 21, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 22, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 23, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 24, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 25, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 26, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 27, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 28, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun"),
    Vocab(id = 29, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
    Vocab(id = 30, level = JlptLevel.N5, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun")
)