package io.kotoba.app.domain.repository

import io.kotoba.app.data.local.toJlptLevel
import io.kotoba.app.data.local.dao.VocabDao
import io.kotoba.app.data.local.entity.VocabEntity
import io.kotoba.app.data.local.entity.VocabProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Vocab
import io.kotoba.app.domain.model.VocabProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private fun VocabEntity.toDomain(): Vocab = Vocab(
    id = id,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    kanji = kanji,
    reading = reading,
    meaning = meaning,
    onyomi = onyomi,
    kunyomi = kunyomi,
    partOfSpeech = partOfSpeech,
    example = example,
    exampleMeaning = exampleMeaning,
    source = source
)

private fun VocabProgressEntity.toDomain(): VocabProgress = VocabProgress(
    vocabId = vocabId,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    learned = learned,
    box = box,
    ease = ease,
    intervalDays = intervalDays,
    dueAt = dueAt,
    lastReviewedAt = lastReviewedAt,
    reviewCount = reviewCount
)

private fun Vocab.toEntity(): VocabEntity = VocabEntity(
    id = id,
    level = level.name,
    kanji = kanji,
    reading = reading,
    meaning = meaning,
    onyomi = onyomi,
    kunyomi = kunyomi,
    partOfSpeech = partOfSpeech,
    example = example,
    exampleMeaning = exampleMeaning,
    source = source
)

private fun VocabProgress.toEntity(): VocabProgressEntity = VocabProgressEntity(
    vocabId = vocabId,
    level = level.name,
    learned = learned,
    box = box,
    ease = ease,
    intervalDays = intervalDays,
    dueAt = dueAt,
    lastReviewedAt = lastReviewedAt,
    reviewCount = reviewCount
)

class VocabRepositoryImpl(
    private val vocabDao: VocabDao
) : VocabRepository {

    override fun getByLevel(level: JlptLevel): Flow<List<Vocab>> =
        vocabDao.getByLevel(level).map { list -> list.map { it.toDomain() } }

    override suspend fun getById(id: Long): Vocab? = vocabDao.getById(id)?.toDomain()

    override suspend fun upsertAll(vararg vocab: Vocab) =
        vocabDao.upsertAll(*vocab.map { it.toEntity() }.toTypedArray())

    override suspend fun upsert(vocab: Vocab) = vocabDao.upsert(vocab.toEntity())

    override fun countByLevel(level: JlptLevel): Flow<Int> = vocabDao.countByLevel(level)

    override fun countLearnedByLevel(level: JlptLevel): Flow<Int> =
        vocabDao.countLearnedByLevel(level)

    override suspend fun getProgress(vocabId: Long): VocabProgress? =
        vocabDao.getProgress(vocabId)?.toDomain()

    override suspend fun getProgressByIds(vocabIds: List<Long>): List<VocabProgress> =
        vocabDao.getProgressByIds(vocabIds).map { it.toDomain() }

    override suspend fun upsertProgress(progress: VocabProgress) =
        vocabDao.upsertProgress(progress.toEntity())

    override suspend fun updateProgress(progress: VocabProgress) =
        vocabDao.updateProgress(progress.toEntity())

    override suspend fun deleteProgress(progress: VocabProgress) =
        vocabDao.deleteProgress(progress.toEntity())

    override suspend fun getDueCards(level: JlptLevel, now: Long): List<VocabProgress> =
        vocabDao.getDueCards(level, now).map { it.toDomain() }

    override suspend fun getLearned(limit: Int): List<VocabProgress> =
        vocabDao.getLearned(limit).map { it.toDomain() }

    override fun countDueCards(level: JlptLevel, now: Long): Flow<Int> =
        vocabDao.countDueCards(level, now)
}

interface VocabRepository {
    fun getByLevel(level: JlptLevel): Flow<List<Vocab>>
    suspend fun getById(id: Long): Vocab?
    suspend fun upsertAll(vararg vocab: Vocab)
    suspend fun upsert(vocab: Vocab)
    fun countByLevel(level: JlptLevel): Flow<Int>
    fun countLearnedByLevel(level: JlptLevel): Flow<Int>
    suspend fun getProgress(vocabId: Long): VocabProgress?
    suspend fun getProgressByIds(vocabIds: List<Long>): List<VocabProgress>
    suspend fun upsertProgress(progress: VocabProgress)
    suspend fun updateProgress(progress: VocabProgress)
    suspend fun deleteProgress(progress: VocabProgress)
    suspend fun getDueCards(level: JlptLevel, now: Long): List<VocabProgress>
    suspend fun getLearned(limit: Int): List<VocabProgress>
    fun countDueCards(level: JlptLevel, now: Long): Flow<Int>
}