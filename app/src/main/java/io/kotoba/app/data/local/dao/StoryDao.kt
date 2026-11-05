package io.kotoba.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.kotoba.app.data.local.entity.StoryEntity
import io.kotoba.app.data.local.entity.StoryProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(vararg entities: StoryEntity)

    @Query("SELECT * FROM story WHERE level = :level ORDER BY id ASC")
    fun getByLevel(level: JlptLevel): Flow<List<StoryEntity>>

    @Query("SELECT * FROM story WHERE id = :id")
    suspend fun getById(id: Long): StoryEntity?

    @Query("SELECT COUNT(*) FROM story WHERE level = :level")
    fun countByLevel(level: JlptLevel): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(entity: StoryProgressEntity)

    @Query("SELECT * FROM story_progress WHERE storyId = :storyId")
    suspend fun getProgress(storyId: Long): StoryProgressEntity?

    @Query("SELECT sp.* FROM story_progress sp JOIN story s ON sp.storyId = s.id WHERE s.level = :level ORDER BY sp.lastReadAt DESC")
    fun getProgressByLevel(level: JlptLevel): Flow<List<StoryProgressEntity>>
}