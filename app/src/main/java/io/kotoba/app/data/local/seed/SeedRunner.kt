package io.kotoba.app.data.local.seed

import io.kotoba.app.data.local.KotobaDatabase
import io.kotoba.app.data.local.entity.UserSettingsEntity
import io.kotoba.app.data.local.entity.VocabEntity
import io.kotoba.app.data.local.entity.KanjiEntity
import io.kotoba.app.data.local.entity.GrammarEntity
import io.kotoba.app.data.local.entity.StoryEntity
import io.kotoba.app.domain.model.JlptLevel
import kotlinx.coroutines.runBlocking

/**
 * Populates the database with the bundled sample dataset on first launch.
 * Called from RoomDatabase.Callback.onCreate so the DB is guaranteed empty.
 */
object SeedRunner {
    fun seed(db: KotobaDatabase) = runBlocking {
        db.settingsDao.upsert(UserSettingsEntity())

        SampleDataSeeder.vocab.forEach { v ->
            db.vocabDao.upsert(
                VocabEntity(
                    id = v.id,
                    level = v.level.name,
                    kanji = v.kanji,
                    reading = v.reading,
                    meaning = v.meaning,
                    partOfSpeech = v.partOfSpeech
                )
            )
        }

        db.kanjiDao.upsertAll(
            *SampleDataSeeder.kanji.map { k ->
                KanjiEntity(
                    character = k.character,
                    level = k.level.name,
                    onyomi = k.onyomi,
                    kunyomi = k.kunyomi,
                    meaning = k.meaning,
                    strokeCount = k.strokeCount,
                    strokeSvg = k.strokeSvg,
                    jlptFrequency = k.jlptFrequency
                )
            }.toTypedArray()
        )

        db.grammarDao.upsertAll(
            *SampleDataSeeder.grammar.map { g ->
                GrammarEntity(
                    id = g.id,
                    level = g.level.name,
                    pattern = g.pattern,
                    reading = g.reading,
                    meaning = g.meaning,
                    explanation = g.explanation,
                    example = g.example,
                    exampleMeaning = g.exampleMeaning
                )
            }.toTypedArray()
        )

        db.storyDao.upsertAll(
            *SampleDataSeeder.stories.map { s ->
                StoryEntity(
                    id = s.id,
                    level = s.level.name,
                    title = s.title,
                    text = s.text,
                    translation = s.translation,
                    vocabIds = s.vocabIds.joinToString(",")
                )
            }.toTypedArray()
        )
    }
}