package io.kotoba.app.ui.vocab

import io.kotoba.app.domain.model.VocabProgress

private const val MIN_EASE = 1.3f
private const val HARD_PENALTY = 0.15f
private const val GOOD_PENALTY = 0.0f
private const val EASY_BONUS = 0.2f

enum class Sm2Rating { AGAIN, HARD, GOOD, EASY }

data class Sm2Result(
    val ease: Float,
    val intervalDays: Int,
    val repetition: Int,
    val dueTimestamp: Long
)

fun Sm2Rating.label(): String = when (this) {
    Sm2Rating.AGAIN -> "Again"
    Sm2Rating.HARD -> "Hard"
    Sm2Rating.GOOD -> "Good"
    Sm2Rating.EASY -> "Easy"
}

fun Sm2Result.intervalLabel(): String = when {
    intervalDays <= 0 -> "<10m"
    intervalDays == 1 -> "1d"
    intervalDays < 7 -> "${intervalDays}d"
    intervalDays < 30 -> "${intervalDays}d"
    else -> "${intervalDays / 7}w"
}

fun scheduleSm2(
    progress: VocabProgress,
    rating: Sm2Rating,
    now: Long = System.currentTimeMillis()
): Sm2Result {
    val rep = progress.reviewCount + 1
    val interval = progress.intervalDays
    val ease = when (rating) {
        Sm2Rating.AGAIN -> (progress.ease - 0.15f).coerceAtLeast(MIN_EASE)
        Sm2Rating.HARD -> (progress.ease - HARD_PENALTY).coerceAtLeast(MIN_EASE)
        Sm2Rating.GOOD -> (progress.ease + GOOD_PENALTY).coerceAtLeast(MIN_EASE)
        Sm2Rating.EASY -> (progress.ease + EASY_BONUS).coerceAtLeast(MIN_EASE)
    }
    val newInterval = when (rating) {
        Sm2Rating.AGAIN -> 0
        Sm2Rating.HARD -> (interval * 1.2f).toInt().coerceAtLeast(1)
        Sm2Rating.EASY -> (interval * 3f).toInt().coerceAtLeast(6)
        Sm2Rating.GOOD -> if (rep == 1) 1 else (interval * ease).toInt().coerceAtLeast(interval)
    }
    val days = if (rating == Sm2Rating.AGAIN) 0 else newInterval
    val nextDue = now + days * 86_400_000L
    return Sm2Result(ease, days, rep, nextDue)
}