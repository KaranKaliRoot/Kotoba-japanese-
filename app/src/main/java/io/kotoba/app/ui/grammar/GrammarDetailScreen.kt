package io.kotoba.app.ui.grammar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.Grammar
import io.kotoba.app.ui.grammar.QuizState
import androidx.compose.foundation.layout.height
import io.kotoba.app.ui.theme.KotobaButter
import io.kotoba.app.ui.theme.KotobaClay
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaInkSoft
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaTerracotta
import io.kotoba.app.ui.theme.KotobaWood
import io.kotoba.app.ui.theme.Shippori

@Composable
fun GrammarDetailScreen(
    grammar: Grammar,
    quizState: QuizState,
    selectedAnswer: Int,
    onAnswer: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        androidx.compose.material3.IconButton(
            onClick = onBack,
            modifier = androidx.compose.ui.Modifier.padding(end = 8.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = KotobaInk
            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = KotobaPaper)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    grammar.pattern,
                    fontFamily = Shippori,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = KotobaInk
                )
                Text(
                    grammar.reading.ifBlank { "—" },
                    fontFamily = Shippori,
                    fontSize = 16.sp,
                    color = KotobaInkSoft
                )
                SpacerRow(8.dp)
                BoxedSection("Formation", KotobaClay) {
                    Text(grammar.explanation.ifBlank { "—" }, fontFamily = Shippori, fontSize = 15.sp, color = KotobaInk)
                }
                if (grammar.nuance.isNotEmpty()) {
                    SpacerRow(8.dp)
                    BoxedSection("Shin Kanzen Nuance", KotobaButter) {
                        Text(grammar.nuance, fontFamily = Shippori, fontSize = 15.sp, color = KotobaInk)
                    }
                }
                if (grammar.example.isNotEmpty()) {
                    SpacerRow(8.dp)
                    BoxedSection("Example", KotobaPaper) {
                        Text(grammar.example, fontFamily = Shippori, fontSize = 15.sp, color = KotobaInk)
                        if (grammar.exampleMeaning.isNotEmpty()) {
                            SpacerRow(4.dp)
                            Text(grammar.exampleMeaning, fontFamily = Shippori, fontSize = 14.sp, color = KotobaInkSoft)
                        }
                    }
                }
                SpacerRow(16.dp)
                QuizSection(grammar, quizState, selectedAnswer, onAnswer)
            }
        }
    }
}

@Composable
private fun BoxedSection(title: String, bg: androidx.compose.ui.graphics.Color, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                title,
                fontFamily = Shippori,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = KotobaWood
            )
            SpacerRow(6.dp)
            content()
        }
    }
}

@Composable
private fun QuizSection(
    g: Grammar,
    state: QuizState,
    selected: Int,
    onAnswer: (Int) -> Unit
) {
    val answers = if (g.exampleMeaning.isNotEmpty())
        listOf(g.exampleMeaning, "Wrong meaning A", "Wrong meaning B", "Wrong meaning C")
    else
        listOf("A", "B", "C", "D")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Practice", fontFamily = Shippori, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = KotobaInk)
        answers.forEachIndexed { i, text ->
            val isSelected = selected == i
            val color = when {
                state == QuizState.CORRECT && i == 0 -> KotobaClay
                state == QuizState.WRONG && isSelected && i != 0 -> KotobaTerracotta
                isSelected -> KotobaButter
                else -> KotobaPaper
            }
            Card(
                onClick = { if (state == QuizState.IDLE) onAnswer(i) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = color),
                elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp)
            ) {
                Text(
                    "${('A'.code + i).toChar()}  $text",
                    modifier = Modifier.padding(16.dp),
                    fontFamily = Shippori,
                    fontSize = 15.sp,
                    color = KotobaInk
                )
            }
        }
    }
}

@Composable
private fun SpacerRow(h: androidx.compose.ui.unit.Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.height(h))
}