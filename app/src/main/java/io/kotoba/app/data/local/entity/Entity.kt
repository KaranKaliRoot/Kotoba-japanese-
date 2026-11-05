package io.kotoba.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.kotoba.app.domain.model.JlptLevel

// ---------- Vocabulary ----------

@Entity(tableName = "vocab")
data class VocabEntity(
    @PrimaryKey val id: Long,
    val level: String,
    val kanji: String,
    val reading: String,
    val meaning: String,
    val onyomi: String = "",
    val kunyomi: String = "",
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String = "",
    val example: String = "",
    @ColumnInfo(name = "example_meaning") val exampleMeaning: String = "",
    val source: String = ""
)

@Entity(tableName = "vocab_progress")
data class VocabProgressEntity(
    @PrimaryKey val vocabId: Long,
    val level: String,
    val learned: Boolean = false,
    val box: Int = 0,
    val ease: Float = 2.5f,
    val intervalDays: Int = 0,
    val dueAt: Long = 0L,
    val lastReviewedAt: Long = 0L,
    val reviewCount: Int = 0
)

// ---------- Kanji ----------

@Entity(tableName = "kanji")
data class KanjiEntity(
    @PrimaryKey val character: String,
    val level: String,
    val onyomi: String = "",
    val kunyomi: String = "",
    val meaning: String = "",
    val strokeCount: Int = 0,
    val strokeSvg: String = "",
    val jlptFrequency: Int = 0,
    val jukugo: String = "",
    val source: String = "kanjivg"
)

@Entity(tableName = "kanji_compound")
data class KanjiCompoundEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val character: String,
    val reading: String,
    val meaning: String,
    val level: String = "N5"
)

@Entity(tableName = "kanji_progress")
data class KanjiProgressEntity(
    @PrimaryKey val character: String,
    val level: String,
    val learned: Boolean = false,
    val strokeCount: Int = 0,
    val lastPracticedAt: Long = 0L
)

// ---------- Grammar ----------

@Entity(tableName = "grammar")
data class GrammarEntity(
    @PrimaryKey val id: Long,
    val level: String,
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

@Entity(tableName = "grammar_progress")
data class GrammarProgressEntity(
    @PrimaryKey val grammarId: Long,
    val level: String,
    val learned: Boolean = false,
    val lastReviewedAt: Long = 0L
)

// ---------- Stories / Reading ----------

@Entity(tableName = "story")
data class StoryEntity(
    @PrimaryKey val id: Long,
    val level: String,
    val title: String,
    val text: String,
    val translation: String,
    val vocabIds: String = "",
    val source: String = ""
)

@Entity(tableName = "story_progress")
data class StoryProgressEntity(
    @PrimaryKey val storyId: Long,
    val level: String,
    val readCount: Int = 0,
    val lastReadAt: Long = 0L
)

// ---------- User settings ----------

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: Int = 0,
    val onboardingCompleted: Boolean = false,
    val dailyCardGoal: Int = 20,
    val activeLevel: String = JlptLevel.N5.name,
    val theme: String = "system",
    val srsEnabled: Boolean = true,
    val showKanaGuide: Boolean = true,
    val lastStudyDay: String = "",
    val streakDays: Int = 0
)