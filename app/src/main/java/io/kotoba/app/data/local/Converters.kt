package io.kotoba.app.data.local

import androidx.room.TypeConverter
import io.kotoba.app.domain.model.JlptLevel

class JlptLevelConverter {
    @TypeConverter
    fun toJlptLevel(value: String?): JlptLevel? =
        value?.let { runCatching { JlptLevel.valueOf(it) }.getOrNull() }

    @TypeConverter
    fun fromJlptLevel(level: JlptLevel?): String? = level?.name
}