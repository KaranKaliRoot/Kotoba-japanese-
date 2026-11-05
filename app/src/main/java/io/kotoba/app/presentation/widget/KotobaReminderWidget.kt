package io.kotoba.app.presentation.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaPaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KotobaReminderWidget : GlanceAppWidget() {

    companion object {
        const val EXTRA_EMOJI = "extra_emoji"
        const val EXTRA_TEXT = "extra_text"

        fun update(context: Context, emoji: String, text: String) {
            val widget = KotobaReminderWidget()
            CoroutineScope(Dispatchers.IO).launch {
                val manager = GlanceAppWidgetManager(context)
                manager.getGlanceIds(KotobaReminderWidget::class.java).forEach { id ->
                    widget.update(context, id)
                }
            }
        }
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val text = "One more card."
        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(KotobaPaper)
                    .padding(16.dp)
            ) {
                Row {
                    Text("📚", modifier = GlanceModifier.size(28.dp), style = TextStyle(color = ColorProvider(KotobaInk)))
                    Spacer(modifier = GlanceModifier.size(8.dp))
                    Text("言葉", style = TextStyle(color = ColorProvider(KotobaInk)))
                }
                Spacer(modifier = GlanceModifier.size(12.dp))
                Text(text, style = TextStyle(color = ColorProvider(KotobaInk)))
            }
        }
    }
}

class KotobaReminderWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = KotobaReminderWidget()
}