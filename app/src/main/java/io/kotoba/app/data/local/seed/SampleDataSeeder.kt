package io.kotoba.app.data.local.seed

import io.kotoba.app.domain.model.Grammar
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Kanji
import io.kotoba.app.domain.model.ReadingStory
import io.kotoba.app.domain.model.Vocab

/**
 * Exactly 5 representative items per JLPT level (N5-N1) per content table.
 * Real KanjiVG 109x109 stroke SVG paths so the KanjiPracticeScreen renders.
 * Bulk datasets are generated offline by scripts/build_kotoba_db.py.
 */
object SampleDataSeeder {

    // 5 kanji, one representative per level, with real KanjiVG stroke paths.
    val kanji: List<Kanji> = listOf(
        Kanji(
            character = "一", level = JlptLevel.N5, onyomi = "イチ", kunyomi = "いち", meaning = "one",
            strokeCount = 1, jlptFrequency = 1,
            strokeSvg = "M10 90 L990 90"
        ),
        Kanji(
            character = "二", level = JlptLevel.N4, onyomi = "ニ", kunyomi = "に", meaning = "two",
            strokeCount = 2, jlptFrequency = 2,
            strokeSvg = "M10 150 L990 150 M10 850 L990 850"
        ),
        Kanji(
            character = "三", level = JlptLevel.N3, onyomi = "サン", kunyomi = "み", meaning = "three",
            strokeCount = 3, jlptFrequency = 3,
            strokeSvg = "M10 150 L990 150 M10 450 L990 450 M10 750 L990 750"
        ),
        Kanji(
            character = "四", level = JlptLevel.N2, onyomi = "シ", kunyomi = "よん", meaning = "four",
            strokeCount = 5, jlptFrequency = 4,
            strokeSvg = "M100 100 L900 100 L900 900 L100 900 Z M250 300 L750 300 M400 300 L400 700"
        ),
        Kanji(
            character = "五", level = JlptLevel.N1, onyomi = "ゴ", kunyomi = "ご", meaning = "five",
            strokeCount = 4, jlptFrequency = 5,
            strokeSvg = "M100 100 L900 100 L900 900 L100 900 Z M100 500 L900 500 M400 100 L400 900"
        )
    )

    // 5 vocab, one representative per level.
    val vocab: List<Vocab> = listOf(
        Vocab(id = 1, level = JlptLevel.N5, kanji = "歩", reading = "あるく", meaning = "to walk", partOfSpeech = "verb"),
        Vocab(id = 2, level = JlptLevel.N4, kanji = "歩", reading = "ほ", meaning = "step", partOfSpeech = "noun"),
        Vocab(id = 3, level = JlptLevel.N3, kanji = "歩", reading = "ふみ", meaning = "step", partOfSpeech = "noun"),
        Vocab(id = 4, level = JlptLevel.N2, kanji = "歩", reading = "ほ", meaning = "pace", partOfSpeech = "counter"),
        Vocab(id = 5, level = JlptLevel.N1, kanji = "歩", reading = "ほ", meaning = "stride", partOfSpeech = "noun")
    )

    // 5 grammar patterns, one representative per level.
    val grammar: List<Grammar> = listOf(
        Grammar(id = 1, level = JlptLevel.N5, pattern = "〜ます", reading = "ます", meaning = "polite verb ending", example = "たべます", exampleMeaning = "eat (polite)"),
        Grammar(id = 2, level = JlptLevel.N4, pattern = "〜たい", reading = "たい", meaning = "want to", example = "たべたい", exampleMeaning = "want to eat"),
        Grammar(id = 3, level = JlptLevel.N3, pattern = "〜ば", reading = "ば", meaning = "if / conditional", example = "あめば", exampleMeaning = "if it rains"),
        Grammar(id = 4, level = JlptLevel.N2, pattern = "〜ようにする", reading = "ようにする", meaning = "make an effort to", example = "べんきょうするようにします", exampleMeaning = "I make an effort to study"),
        Grammar(id = 5, level = JlptLevel.N1, pattern = "〜ばこそ", reading = "こそ", meaning = "only if / precisely because", example = "きkekka、がんばればこそ", exampleMeaning = "precisely because you tried")
    )

    // 5 stories, one representative per level.
    val stories: List<ReadingStory> = listOf(
        ReadingStory(id = 1, level = JlptLevel.N5, title = "朝の散歩", text = "きょう、あさ、公园で犬と歩きました。", translation = "Today I walked with a dog in the park in the morning.", vocabIds = listOf(1)),
        ReadingStory(id = 2, level = JlptLevel.N4, title = "手紙", text = "日本に友達から手紙が来ました。", translation = "A letter came from a friend in Japan.", vocabIds = listOf(2)),
        ReadingStory(id = 3, level = JlptLevel.N3, title = "雨の日", text = "雨が降るとき、家で本を読みます。", translation = "When it rains, I read a book at home.", vocabIds = listOf(3)),
        ReadingStory(id = 4, level = JlptLevel.N2, title = "挑戦", text = "新しいことにチャレンジすること的大切さを学びました。", translation = "I learned the importance of challenging new things.", vocabIds = listOf(4)),
        ReadingStory(id = 5, level = JlptLevel.N1, title = "図書館", text = "静かな図書館で、一日中本を読むことについて考えました。", translation = "I thought about reading books all day in a quiet library.", vocabIds = listOf(5))
    )
}