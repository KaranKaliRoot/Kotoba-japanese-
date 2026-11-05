package io.kotoba.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.kotoba.app.data.local.entity.KanjiEntity
import io.kotoba.app.data.local.entity.KanjiProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface KanjiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(vararg entities: KanjiEntity)

    @Query("SELECT * FROM kanji WHERE level = :level ORDER BY jlptFrequency ASC")
    fun getByLevel(level: JlptLevel): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji ORDER BY jlptFrequency ASC")
    fun getAll(): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji WHERE character LIKE '%' || :query || '%' OR meaning LIKE '%' || :query || '%' ORDER BY jlptFrequency ASC")
    suspend fun search(query: String): List<KanjiEntity>

    @Query("SELECT * FROM kanji WHERE character = :character")
    suspend fun getByChar(character: String): KanjiEntity?

    @Query("SELECT * FROM kanji WHERE character IN (:chars)")
    suspend fun getByChars(chars: List<String>): List<KanjiEntity>

    @Query("SELECT COUNT(*) FROM kanji WHERE level = :level")
    fun countByLevel(level: JlptLevel): Flow<Int>

    @Query("SELECT COUNT(*) FROM kanji_progress kp JOIN kanji k ON kp.character = k.character WHERE k.level = :level AND kp.learned = 1")
    fun countLearnedByLevel(level: JlptLevel): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(entity: KanjiProgressEntity)

    @Query("SELECT * FROM kanji_progress WHERE character = :character")
    suspend fun getProgress(character: String): KanjiProgressEntity?

    @Query("SELECT * FROM kanji_progress WHERE learned = 1")
    suspend fun getLearned(): List<KanjiProgressEntity>

    @Query("SELECT COUNT(*) FROM kanji_progress WHERE learned = 1")
    fun learnedCount(): Flow<Int>
}