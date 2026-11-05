package io.kotoba.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.kotoba.app.data.local.entity.GrammarEntity
import io.kotoba.app.data.local.entity.GrammarProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface GrammarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(vararg entities: GrammarEntity)

    @Query("SELECT * FROM grammar WHERE level = :level ORDER BY id ASC")
    fun getByLevel(level: JlptLevel): Flow<List<GrammarEntity>>

    @Query("SELECT * FROM grammar WHERE id = :id")
    suspend fun getById(id: Long): GrammarEntity?

    @Query("SELECT COUNT(*) FROM grammar WHERE level = :level")
    fun countByLevel(level: JlptLevel): Flow<Int>

    @Query("SELECT COUNT(*) FROM grammar_progress gp JOIN grammar g ON gp.grammarId = g.id WHERE g.level = :level AND gp.learned = 1")
    fun countLearnedByLevel(level: JlptLevel): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(entity: GrammarProgressEntity)

    @Query("SELECT * FROM grammar_progress WHERE grammarId = :grammarId")
    suspend fun getProgress(grammarId: Long): GrammarProgressEntity?

    @Query("SELECT * FROM grammar_progress WHERE learned = 1")
    suspend fun getLearned(): List<GrammarProgressEntity>

    @Query("SELECT COUNT(*) FROM grammar_progress WHERE learned = 1")
    fun learnedCount(): Flow<Int>
}