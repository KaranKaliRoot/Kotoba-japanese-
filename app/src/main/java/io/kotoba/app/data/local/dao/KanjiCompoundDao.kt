package io.kotoba.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.kotoba.app.data.local.entity.KanjiCompoundEntity
import io.kotoba.app.domain.model.JlptLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface KanjiCompoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(vararg entities: KanjiCompoundEntity)

    @Query("SELECT * FROM kanji_compound WHERE character = :character ORDER BY id ASC")
    fun getByCharacter(character: String): Flow<List<KanjiCompoundEntity>>

    @Query("SELECT * FROM kanji_compound WHERE character = :character AND level = :level ORDER BY id ASC")
    suspend fun getByCharacterAndLevel(character: String, level: JlptLevel): List<KanjiCompoundEntity>
}