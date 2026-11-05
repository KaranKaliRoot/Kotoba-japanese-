package io.kotoba.app.ui.vocab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.Vocab
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaInkSoft
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaWood
import io.kotoba.app.ui.theme.Shippori

@Composable
fun VocabWordListView(
    items: List<Vocab>,
    query: String,
    showReading: Boolean,
    showMeaning: Boolean,
    onQueryChange: (String) -> Unit,
    onToggleReading: () -> Unit,
    onToggleMeaning: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filtered = items.filter {
        query.isEmpty() ||
            it.kanji.contains(query, true) ||
            it.reading.contains(query, true) ||
            it.meaning.contains(query, true)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Search words...", color = KotobaInkSoft) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = KotobaPaper,
                unfocusedContainerColor = KotobaPaper,
                focusedIndicatorColor = KotobaWood,
                unfocusedIndicatorColor = KotobaWood
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = showReading,
                onClick = onToggleReading,
                label = { Text("Reading") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = KotobaInk,
                    labelColor = KotobaPaper,
                    selectedLabelColor = KotobaPaper
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            )
            FilterChip(
                selected = showMeaning,
                onClick = onToggleMeaning,
                label = { Text("Meaning") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = KotobaInk,
                    labelColor = KotobaPaper,
                    selectedLabelColor = KotobaPaper
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            )
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered) { vocab ->
                VocabRow(vocab, showReading, showMeaning)
            }
        }
    }
}

@Composable
private fun VocabRow(vocab: Vocab, showReading: Boolean, showMeaning: Boolean) {
    Card(
        onClick = {},
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = KotobaPaper)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vocab.kanji,
                    fontFamily = Shippori,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = KotobaInk
                )
                Text(
                    text = vocab.level.code,
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    color = KotobaWood
                )
            }
            if (showReading) {
                Text(
                    text = vocab.reading,
                    fontFamily = Shippori,
                    fontSize = 15.sp,
                    color = KotobaInkSoft
                )
            }
            if (showMeaning) {
                Text(
                    text = vocab.meaning,
                    fontFamily = Shippori,
                    fontSize = 15.sp,
                    color = KotobaInkSoft
                )
            }
        }
    }
}