package io.kotoba.app.domain.usecase

import io.kotoba.app.domain.model.Quote
import java.util.Calendar

class HourlyQuoteUseCase {

    fun quote(now: Long = System.currentTimeMillis()): Quote {
        val hour = Calendar.getInstance().apply { timeInMillis = now }.get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 4..8 -> Quote("☕", "Good morning. One kanji a day keeps the N1 away.")
            in 9..11 -> Quote("☀️", "Morning is for drilling vocab. Let's go.")
            in 12..16 -> Quote("🌤️", "Afternoon slump? Flip five cards.")
            in 17..21 -> Quote("🌙", "Evening is your best study hour. Use it.")
            else -> Quote("🌙", "Don't give up on me tonight. One more card.")
        }
    }
}