package io.kotoba.app.domain.repository

import io.kotoba.app.data.local.toJlptLevel
import io.kotoba.app.data.local.dao.KanjiCompoundDao
import io.kotoba.app.data.local.dao.KanjiDao
import io.kotoba.app.data.local.entity.KanjiCompoundEntity
import io.kotoba.app.data.local.entity.KanjiEntity
import io.kotoba.app.data.local.entity.KanjiProgressEntity
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Kanji
import io.kotoba.app.domain.model.KanjiCompound
import io.kotoba.app.domain.model.KanjiProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private fun KanjiEntity.toDomain(): Kanji = Kanji(
    character = character,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    onyomi = onyomi,
    kunyomi = kunyomi,
    meaning = meaning,
    strokeCount = strokeCount,
    strokeSvg = strokeSvg,
    jlptFrequency = jlptFrequency,
    source = source
)

private fun KanjiProgressEntity.toDomain(): KanjiProgress = KanjiProgress(
    character = character,
    level = level.toJlptLevel() ?: JlptLevel.N5,
    learned = learned,
    strokeCount = strokeCount,
    lastPracticedAt = lastPracticedAt
)

private fun Kanji.toEntity(): KanjiEntity = KanjiEntity(
    character = character,
    level = level.name,
    onyomi = onyomi,
    kunyomi = kunyomi,
    meaning = meaning,
    strokeCount = strokeCount,
    strokeSvg = strokeSvg,
    jlptFrequency = jlptFrequency,
    source = source
)

private fun KanjiProgress.toEntity(): KanjiProgressEntity = KanjiProgressEntity(
    character = character,
    level = level.name,
    learned = learned,
    strokeCount = strokeCount,
    lastPracticedAt = lastPracticedAt
)

private fun KanjiCompoundEntity.toDomain(): KanjiCompound = KanjiCompound(
    character = character,
    reading = reading,
    meaning = meaning,
    level = level.toJlptLevel() ?: JlptLevel.N5
)

class KanjiRepositoryImpl(
    private val kanjiDao: KanjiDao,
    private val kanjiCompoundDao: KanjiCompoundDao
) : KanjiRepository {

    override fun getByLevel(level: JlptLevel): Flow<List<Kanji>> =
        kanjiDao.getByLevel(level).map { it.map(KanjiEntity::toDomain) }

    override fun getAll(): Flow<List<Kanji>> =
        kanjiDao.getAll().map { it.map(KanjiEntity::toDomain) }

    override suspend fun search(query: String): List<Kanji> =
        kanjiDao.search(query).map(KanjiEntity::toDomain)

    override suspend fun getByChar(character: String): Kanji? =
        kanjiDao.getByChar(character)?.toDomain()

    override suspend fun getByChars(chars: List<String>): List<Kanji> =
        kanjiDao.getByChars(chars).map(KanjiEntity::toDomain)

    override fun countByLevel(level: JlptLevel): Flow<Int> = kanjiDao.countByLevel(level)

    override fun countLearnedByLevel(level: JlptLevel): Flow<Int> =
        kanjiDao.countLearnedByLevel(level)

    override suspend fun upsertAll(vararg kanji: Kanji) =
        kanjiDao.upsertAll(*kanji.map { it.toEntity() }.toTypedArray())

    override suspend fun upsertProgress(progress: KanjiProgress) =
        kanjiDao.upsertProgress(progress.toEntity())

    override suspend fun getProgress(character: String): KanjiProgress? =
        kanjiDao.getProgress(character)?.toDomain()

    override suspend fun getLearned(): List<KanjiProgress> =
        kanjiDao.getLearned().map(KanjiProgressEntity::toDomain)

    override fun learnedCount(): Flow<Int> = kanjiDao.learnedCount()

    override fun getCompounds(character: String): Flow<List<KanjiCompound>> =
        kanjiCompoundDao.getByCharacter(character).map { it.map(KanjiCompoundEntity::toDomain) }

    override suspend fun getCompoundsByLevel(character: String, level: JlptLevel): List<KanjiCompound> =
        kanjiCompoundDao.getByCharacterAndLevel(character, level).map(KanjiCompoundEntity::toDomain)
}

interface KanjiRepository {
    fun getByLevel(level: JlptLevel): Flow<List<Kanji>>
    fun getAll(): Flow<List<Kanji>>
    suspend fun search(query: String): List<Kanji>
    suspend fun getByChar(character: String): Kanji?
    suspend fun getByChars(chars: List<String>): List<Kanji>
    fun countByLevel(level: JlptLevel): Flow<Int>
    fun countLearnedByLevel(level: JlptLevel): Flow<Int>
    suspend fun upsertAll(vararg kanji: Kanji)
    suspend fun upsertProgress(progress: KanjiProgress)
    suspend fun getProgress(character: String): KanjiProgress?
    suspend fun getLearned(): List<KanjiProgress>
    fun learnedCount(): Flow<Int>
    fun getCompounds(character: String): Flow<List<KanjiCompound>>
    suspend fun getCompoundsByLevel(character: String, level: JlptLevel): List<KanjiCompound>
}