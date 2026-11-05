package io.kotoba.app.ui.reading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.ReadingStory
import io.kotoba.app.domain.model.Vocab
import io.kotoba.app.ui.theme.KotobaClay
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaInkSoft
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaTerracotta
import io.kotoba.app.ui.theme.Shippori

@Composable
fun StoryReaderScreen(
    story: ReadingStory,
    showFurigana: Boolean,
    showTranslation: Boolean,
    tappedWord: Vocab?,
    onToggleFurigana: () -> Unit,
    onToggleTranslation: () -> Unit,
    onTapWord: (Vocab) -> Unit,
    onClosePopup: () -> Unit,
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
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = showFurigana,
                onClick = onToggleFurigana,
                label = { Text("Furigana") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = KotobaInk,
                    labelColor = KotobaPaper,
                    selectedLabelColor = KotobaPaper
                ),
                shape = RoundedCornerShape(50)
            )
            FilterChip(
                selected = showTranslation,
                onClick = onToggleTranslation,
                label = { Text("Translation") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = KotobaInk,
                    labelColor = KotobaPaper,
                    selectedLabelColor = KotobaPaper
                ),
                shape = RoundedCornerShape(50)
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
                    story.title,
                    fontFamily = Shippori,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = KotobaInk
                )
                SpacerRow(12.dp)
                Text(
                    story.text,
                    fontFamily = Shippori,
                    fontSize = 18.sp,
                    color = KotobaInk,
                    lineHeight = 32.sp
                )
                if (showTranslation) {
                    SpacerRow(16.dp)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = KotobaClay),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Text(
                            story.translation,
                            modifier = Modifier.padding(14.dp),
                            fontFamily = Shippori,
                            fontSize = 16.sp,
                            color = KotobaInkSoft
                        )
                    }
                }
            }
        }
        if (tappedWord != null) {
            WordPopup(tappedWord, onClosePopup)
        }
    }
}

@Composable
private fun WordPopup(word: Vocab, onClose: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = KotobaTerracotta)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    word.kanji,
                    fontFamily = Shippori,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = KotobaPaper
                )
                androidx.compose.material3.IconButton(onClick = onClose) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = KotobaPaper
                    )
                }
            }
            SpacerRow(8.dp)
            Text(
                word.reading,
                fontFamily = Shippori,
                fontSize = 16.sp,
                color = KotobaPaper.copy(alpha = 0.9f)
            )
            SpacerRow(4.dp)
            Text(
                word.meaning,
                fontFamily = Shippori,
                fontSize = 15.sp,
                color = KotobaPaper
            )
        }
    }
}

@Composable
private fun SpacerRow(h: androidx.compose.ui.unit.Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.height(h))
}