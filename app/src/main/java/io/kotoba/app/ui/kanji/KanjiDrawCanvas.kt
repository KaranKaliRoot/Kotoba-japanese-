package io.kotoba.app.ui.kanji

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke as DrawStroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import io.kotoba.app.domain.model.Kanji
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaTerracotta
import io.kotoba.app.ui.theme.KotobaWood

private const val GRID = 4
private const val START_DIST = 18f
private const val DIRECTION_COS = 0.55f
private const val CORRIDOR = 26f

@Composable
fun KanjiDrawCanvas(
    kanji: Kanji,
    modifier: Modifier = Modifier,
    onDone: (Boolean) -> Unit = {}
) {
    var size by remember { mutableStateOf(0) }
    val bounds = remember(size) {
        if (size <= 0) Rect(0f, 0f, 1f, 1f)
        else {
            val m = size * 0.12f
            Rect(m, m, size - m, size - m)
        }
    }
    val strokes = remember(kanji.strokeSvg, bounds) {
        parseKanjiSvg(kanji.strokeSvg, bounds)
    }
    var current by remember { mutableStateOf(0) }
    var flash by remember { mutableStateOf(false) }
    val hint = remember { Animatable(0f) }
    val drawn = remember { mutableListOf<Path>() }

    LaunchedEffect(flash) {
        if (flash) {
            hint.animateTo(1f, tween(600))
            hint.animateTo(0f, tween(600))
            flash = false
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size = it.width }
            .pointerInput(strokes) {
                awaitPointerEventScope {
                    while (true) {
                        val e = awaitPointerEvent()
                        val p = e.changes.first().position
                        val s = strokes.getOrNull(current) ?: continue
                        val dist = (p - s.start).getDistance()
                        val toEnd = s.end - s.start
                        val dir = if (dist > 0f) ((p.x - s.start.x) * toEnd.x + (p.y - s.start.y) * toEnd.y) / (dist * toEnd.getDistance().coerceAtLeast(1f)) else 0f
                        val corridor = s.samples.minOfOrNull { (it - p).getDistance() } ?: 0f
                        if (dist > START_DIST && dir > DIRECTION_COS && corridor < CORRIDOR) {
                            drawn.add(s.path); current++
                            if (current >= strokes.size) onDone(true)
                        } else { flash = true }
                    }
                }
            }
    ) {
        drawRiceGrid(bounds)
        drawn.forEach { drawPath(it, KotobaInk) }
        strokes.getOrNull(current)?.let { target ->
            drawPath(target.path, Color.Red, style = DrawStroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f))))
            drawCircle(KotobaWood, radius = 6f, center = target.start)
            val hintP = target.start + (target.end - target.start) * hint.value
            drawCircle(KotobaTerracotta, radius = 8f * hint.value + 2f, center = hintP)
        }
    }
}

private fun DrawScope.drawRiceGrid(bounds: Rect) {
    drawRect(KotobaPaper, topLeft = Offset(bounds.left, bounds.top), size = Size(bounds.width, bounds.height))
    val g = bounds.width / GRID
    for (i in 1 until GRID) {
        val x = bounds.left + i * g
        drawLine(KotobaWood, Offset(x, bounds.top), Offset(x, bounds.bottom), 2f)
        val y = bounds.top + i * g
        drawLine(KotobaWood, Offset(bounds.left, y), Offset(bounds.right, y), 2f)
    }
    val c = bounds.center
    drawLine(KotobaWood, Offset(bounds.left, c.y), Offset(bounds.right, c.y), 1.5f)
    drawLine(KotobaWood, Offset(c.x, bounds.top), Offset(c.x, bounds.bottom), 1.5f)
}
