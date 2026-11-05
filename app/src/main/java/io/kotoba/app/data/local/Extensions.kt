package io.kotoba.app.data.local

import io.kotoba.app.domain.model.JlptLevel

fun String.toJlptLevel(): JlptLevel? = runCatching { JlptLevel.valueOf(this) }.getOrNull()