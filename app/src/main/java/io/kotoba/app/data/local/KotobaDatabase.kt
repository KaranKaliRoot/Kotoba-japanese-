package io.kotoba.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.kotoba.app.data.local.dao.GrammarDao
import io.kotoba.app.data.local.dao.KanjiCompoundDao
import io.kotoba.app.data.local.dao.KanjiDao
import io.kotoba.app.data.local.dao.SettingsDao
import io.kotoba.app.data.local.dao.StoryDao
import io.kotoba.app.data.local.dao.VocabDao
import io.kotoba.app.data.local.entity.*

@Database(
    entities = [
        VocabEntity::class,
        VocabProgressEntity::class,
        KanjiEntity::class,
        KanjiProgressEntity::class,
        KanjiCompoundEntity::class,
        GrammarEntity::class,
        GrammarProgressEntity::class,
        StoryEntity::class,
        StoryProgressEntity::class,
        UserSettingsEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(JlptLevelConverter::class)
abstract class KotobaDatabase : RoomDatabase() {
    abstract val vocabDao: VocabDao
    abstract val kanjiDao: KanjiDao
    abstract val kanjiCompoundDao: KanjiCompoundDao
    abstract val grammarDao: GrammarDao
    abstract val storyDao: StoryDao
    abstract val settingsDao: SettingsDao

    companion object {
        const val DATABASE_NAME = "kotoba.db"
        private var INSTANCE: KotobaDatabase? = null

        fun getInstance(context: Context): KotobaDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(KotobaDatabase::class) {
                if (INSTANCE != null) return INSTANCE!!
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    KotobaDatabase::class.java,
                    DATABASE_NAME
                )
                    .addTypeConverter(JlptLevelConverter())
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}

