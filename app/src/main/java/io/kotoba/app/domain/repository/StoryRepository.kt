package io.kotoba.app.domain.repository

import io.kotoba.app.data.local.toJlptLevel
import io.kotoba.app.data.local.dao.StoryDao
import io.kotoba.app.data.local.entity.StoryEntity
import io.kotoba.app.data.local.entity.StoryProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.ReadingStory
import io.kotoba.app.domain.model.StoryProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private fun StoryEntity.toDomain(): ReadingStory = ReadingStory(
    id = id,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    title = title,
    text = text,
    translation = translation,
    vocabIds = vocabIds.split(',').mapNotNull { it.toLongOrNull() },
    source = source
)

private fun StoryProgressEntity.toDomain(): StoryProgress = StoryProgress(
    storyId = storyId,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    readCount = readCount,
    lastReadAt = lastReadAt
)

private fun ReadingStory.toEntity(): StoryEntity = StoryEntity(
    id = id,
    level = level.name,
    title = title,
    text = text,
    translation = translation,
    vocabIds = vocabIds.joinToString(","),
    source = source
)

private fun StoryProgress.toEntity(): StoryProgressEntity = StoryProgressEntity(
    storyId = storyId,
    level = level.name,
    readCount = readCount,
    lastReadAt = lastReadAt
)

class StoryRepositoryImpl(
    private val storyDao: StoryDao
) : StoryRepository {

    override fun getByLevel(level: JlptLevel): Flow<List<ReadingStory>> =
        storyDao.getByLevel(level).map { it.map(StoryEntity::toDomain) }

    override suspend fun getById(id: Long): ReadingStory? = storyDao.getById(id)?.toDomain()

    override suspend fun upsertAll(vararg stories: ReadingStory) =
        storyDao.upsertAll(*stories.map { it.toEntity() }.toTypedArray())

    override fun countByLevel(level: JlptLevel): Flow<Int> = storyDao.countByLevel(level)

    override suspend fun upsertProgress(progress: StoryProgress) =
        storyDao.upsertProgress(progress.toEntity())

    override suspend fun getProgress(storyId: Long): StoryProgress? =
        storyDao.getProgress(storyId)?.toDomain()

    override fun getProgressByLevel(level: JlptLevel): Flow<List<StoryProgress>> =
        storyDao.getProgressByLevel(level).map { it.map(StoryProgressEntity::toDomain) }
}

interface StoryRepository {
    fun getByLevel(level: JlptLevel): Flow<List<ReadingStory>>
    suspend fun getById(id: Long): ReadingStory?
    suspend fun upsertAll(vararg stories: ReadingStory)
    fun countByLevel(level: JlptLevel): Flow<Int>
    suspend fun upsertProgress(progress: StoryProgress)
    suspend fun getProgress(storyId: Long): StoryProgress?
    fun getProgressByLevel(level: JlptLevel): Flow<List<StoryProgress>>
}