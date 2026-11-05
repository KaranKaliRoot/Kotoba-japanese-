package io.kotoba.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.kotoba.app.data.local.entity.VocabEntity
import io.kotoba.app.data.local.entity.VocabProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(vararg entities: VocabEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: VocabEntity)

    @Query("SELECT * FROM vocab WHERE level = :level ORDER BY id ASC")
    fun getByLevel(level: JlptLevel): Flow<List<VocabEntity>>

    @Query("SELECT * FROM vocab WHERE id = :id")
    suspend fun getById(id: Long): VocabEntity?

    @Query("SELECT * FROM vocab WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Long>): List<VocabEntity>

    @Query("SELECT COUNT(*) FROM vocab WHERE level = :level")
    fun countByLevel(level: JlptLevel): Flow<Int>

    @Query("SELECT COUNT(*) FROM vocab_progress vp JOIN vocab v ON vp.vocabId = v.id WHERE v.level = :level AND vp.learned = 1")
    fun countLearnedByLevel(level: JlptLevel): Flow<Int>

    @Query("SELECT * FROM vocab_progress WHERE vocabId = :vocabId")
    suspend fun getProgress(vocabId: Long): VocabProgressEntity?

    @Query("SELECT * FROM vocab_progress WHERE vocabId IN (:vocabIds)")
    suspend fun getProgressByIds(vocabIds: List<Long>): List<VocabProgressEntity>

    @Query("SELECT * FROM vocab_progress WHERE learned = 1 ORDER BY lastReviewedAt DESC LIMIT :limit")
    suspend fun getLearned(limit: Int = 100): List<VocabProgressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(entity: VocabProgressEntity)

    @Update
    suspend fun updateProgress(entity: VocabProgressEntity)

    @Delete
    suspend fun deleteProgress(entity: VocabProgressEntity)

    @Query("SELECT vp.* FROM vocab_progress vp JOIN vocab v ON vp.vocabId = v.id WHERE vp.dueAt <= :now AND v.level = :level ORDER BY vp.dueAt ASC")
    suspend fun getDueCards(level: JlptLevel, now: Long): List<VocabProgressEntity>

    @Query("SELECT COUNT(*) FROM vocab_progress vp JOIN vocab v ON vp.vocabId = v.id WHERE vp.dueAt <= :now AND v.level = :level")
    fun countDueCards(level: JlptLevel, now: Long): Flow<Int>
}