package io.kotoba.app.ui.kanji

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.Kanji
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaSand
import io.kotoba.app.ui.theme.Shippori

@Composable
fun KanjiGridScreen(
    items: List<Kanji>,
    query: String,
    level: JlptLevel?,
    onQuery: (String) -> Unit,
    onLevel: (JlptLevel?) -> Unit,
    onSelected: (Kanji) -> Unit,
    modifier: Modifier = Modifier
) {
    val filtered = items.filter { k ->
        (level == null || k.level == level) &&
        (query.isBlank() || k.character.contains(query) || k.meaning.contains(query, ignoreCase = true))
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = level == null,
                onClick = { onLevel(null) },
                label = { Text("All", fontSize = 11.sp) },
                colors = chipColors(level == null),
                shape = RoundedCornerShape(50)
            )
            JlptLevel.entries.forEach { lvl ->
                FilterChip(
                    selected = level == lvl,
                    onClick = { onLevel(lvl) },
                    label = { Text(lvl.code, fontSize = 11.sp) },
                    colors = chipColors(level == lvl),
                    shape = RoundedCornerShape(50)
                )
            }
        }
        TextField(
            value = query,
            onValueChange = onQuery,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search kanji by glyph or meaning", color = KotobaInk.copy(0.5f)) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = KotobaInk,
                unfocusedIndicatorColor = KotobaInk.copy(0.4f),
                cursorColor = KotobaInk,
                focusedContainerColor = KotobaPaper,
                unfocusedContainerColor = KotobaPaper
            ),
            shape = RoundedCornerShape(12),
            singleLine = true
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filtered) { k -> KanjiCell(k, onSelected) }
        }
    }
}

@Composable
private fun chipColors(selected: Boolean) = FilterChipDefaults.filterChipColors(
    selectedContainerColor = KotobaInk,
    selectedLabelColor = KotobaPaper,
    labelColor = KotobaInk,
    disabledLabelColor = KotobaInk.copy(0.5f)
)

@Composable
private fun KanjiCell(k: Kanji, onSelected: (Kanji) -> Unit) {
    KotobaPaper
    androidx.compose.material3.Card(
        onClick = { onSelected(k) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = KotobaSand),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                k.character,
                fontFamily = Shippori,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
                color = KotobaInk,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(k.level.code, fontSize = 10.sp, color = KotobaInk.copy(0.6f))
        }
    }
}