package io.kotoba.app.domain.repository

import io.kotoba.app.data.local.toJlptLevel
import io.kotoba.app.data.local.dao.GrammarDao
import io.kotoba.app.data.local.entity.GrammarEntity
import io.kotoba.app.data.local.entity.GrammarProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Grammar
import io.kotoba.app.domain.model.GrammarProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private fun GrammarEntity.toDomain(): Grammar = Grammar(
    id = id,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    pattern = pattern,
    reading = reading,
    meaning = meaning,
    explanation = explanation,
    example = example,
    exampleMeaning = exampleMeaning,
    source = source,
    week = week,
    day = day,
    category = category,
    nuance = nuance
)

private fun GrammarProgressEntity.toDomain(): GrammarProgress = GrammarProgress(
    grammarId = grammarId,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    learned = learned,
    lastReviewedAt = lastReviewedAt
)

private fun Grammar.toEntity(): GrammarEntity = GrammarEntity(
    id = id,
    level = level.name,
    pattern = pattern,
    reading = reading,
    meaning = meaning,
    explanation = explanation,
    example = example,
    exampleMeaning = exampleMeaning,
    source = source
)

private fun GrammarProgress.toEntity(): GrammarProgressEntity = GrammarProgressEntity(
    grammarId = grammarId,
    level = level.name,
    learned = learned,
    lastReviewedAt = lastReviewedAt
)

class GrammarRepositoryImpl(
    private val grammarDao: GrammarDao
) : GrammarRepository {

    override fun getByLevel(level: JlptLevel): Flow<List<Grammar>> =
        grammarDao.getByLevel(level).map { it.map(GrammarEntity::toDomain) }

    override suspend fun getById(id: Long): Grammar? = grammarDao.getById(id)?.toDomain()

    override suspend fun upsertAll(vararg grammar: Grammar) =
        grammarDao.upsertAll(*grammar.map { it.toEntity() }.toTypedArray())

    override fun countByLevel(level: JlptLevel): Flow<Int> = grammarDao.countByLevel(level)

    override fun countLearnedByLevel(level: JlptLevel): Flow<Int> =
        grammarDao.countLearnedByLevel(level)

    override suspend fun upsertProgress(progress: GrammarProgress) =
        grammarDao.upsertProgress(progress.toEntity())

    override suspend fun getProgress(grammarId: Long): GrammarProgress? =
        grammarDao.getProgress(grammarId)?.toDomain()

    override suspend fun getLearned(): List<GrammarProgress> =
        grammarDao.getLearned().map(GrammarProgressEntity::toDomain)

    override fun learnedCount(): Flow<Int> = grammarDao.learnedCount()
}

interface GrammarRepository {
    fun getByLevel(level: JlptLevel): Flow<List<Grammar>>
    suspend fun getById(id: Long): Grammar?
    suspend fun upsertAll(vararg grammar: Grammar)
    fun countByLevel(level: JlptLevel): Flow<Int>
    fun countLearnedByLevel(level: JlptLevel): Flow<Int>
    suspend fun upsertProgress(progress: GrammarProgress)
    suspend fun getProgress(grammarId: Long): GrammarProgress?
    suspend fun getLearned(): List<GrammarProgress>
    fun learnedCount(): Flow<Int>
}