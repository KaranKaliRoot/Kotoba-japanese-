package io.kotoba.app.ui.vocab

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.Vocab
import io.kotoba.app.domain.model.VocabProgress
import io.kotoba.app.ui.theme.KotobaButter
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaInkSoft
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaTerracotta
import io.kotoba.app.ui.theme.KotobaWood
import io.kotoba.app.ui.theme.Shippori

@Composable
fun VocabSrsCardView(
    vocab: Vocab,
    progress: VocabProgress,
    showReading: Boolean,
    showMeaning: Boolean,
    onRated: (Sm2Rating) -> Unit,
    modifier: Modifier = Modifier
) {
    val flipped = remember { Animatable(0f) }
    val target by rememberUpdatedState(showReading || showMeaning)

    LaunchedEffect(target) {
        if (!target) flipped.animateTo(1f, tween(200))
    }

    val rotation = flipped.value * 180f
    val isBack = rotation > 90f

    Card(
        onClick = { onRated(Sm2Rating.GOOD) },
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = KotobaPaper)
    ) {
        if (isBack) {
            BackContent(vocab, showReading, showMeaning)
        } else {
            FrontContent(vocab, progress)
        }
    }
}

@Composable
private fun FrontContent(vocab: Vocab, progress: VocabProgress) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = vocab.kanji,
            fontFamily = Shippori,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            color = KotobaInk
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = vocab.level.code,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
            color = KotobaWood
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tap card to flip",
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            color = KotobaInkSoft
        )
    }
}

@Composable
private fun BackContent(vocab: Vocab, showReading: Boolean, showMeaning: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showReading) {
            Text(
                text = vocab.reading,
                fontFamily = Shippori,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = KotobaInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (showMeaning) {
            Text(
                text = vocab.meaning,
                fontFamily = Shippori,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = KotobaInkSoft,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (vocab.example.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = vocab.example,
                fontFamily = Shippori,
                fontSize = 15.sp,
                color = KotobaWood,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun Sm2RatingRow(
    onRated: (Sm2Rating) -> Unit,
    modifier: Modifier = Modifier
) {
    val ratings = listOf(
        Sm2Rating.AGAIN to KotobaTerracotta,
        Sm2Rating.HARD to KotobaWood,
        Sm2Rating.GOOD to KotobaButter,
        Sm2Rating.EASY to KotobaInk
    )
    androidx.compose.foundation.layout.Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ratings.forEach { (rating, tint) ->
            FilterChip(
                selected = false,
                onClick = { onRated(rating) },
                label = { Text(rating.label()) },
                leadingIcon = null,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = tint.copy(alpha = 0.18f),
                    labelColor = KotobaInk
                ),
                shape = RoundedCornerShape(50),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = false,
                    borderColor = tint,
                    selectedBorderColor = tint,
                    disabledBorderColor = tint,
                    disabledSelectedBorderColor = tint
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}