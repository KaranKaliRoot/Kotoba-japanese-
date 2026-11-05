package io.kotoba.app.presentation.widget

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import io.kotoba.app.KotobaApp
import io.kotoba.app.domain.usecase.HourlyQuoteUseCase

class HourlyQuoteWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val quoteUseCase: HourlyQuoteUseCase
        get() = KotobaApp.container.hourlyQuoteUseCase

    override suspend fun doWork(): Result {
        val quote = quoteUseCase.quote(System.currentTimeMillis())
        KotobaReminderWidget.update(applicationContext, quote.emoji, quote.text)
        return Result.success()
    }
}