package io.kotoba.app.util

fun formatStreak(days: Int): String = "$days day${if (days == 1) "" else "s"} streak"

fun formatCount(count: Int): String = count.toString()