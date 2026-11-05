package io.kotoba.app.ui.reading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.ReadingStory
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaInkSoft
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaWood
import io.kotoba.app.ui.theme.Shippori

@Composable
fun StoryListScreen(
    vm: StoryViewModel,
    onSelected: (ReadingStory) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by vm.ui.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "${state.level.code} Stories",
            fontFamily = Shippori,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            color = KotobaInk
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.stories) { story ->
                StoryCard(story, onClick = { onSelected(story) })
            }
        }
    }
}

@Composable
private fun StoryCard(story: ReadingStory, onClick: (ReadingStory) -> Unit) {
    Card(
        onClick = { onClick(story) },
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = KotobaPaper)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    story.title,
                    fontFamily = Shippori,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = KotobaInk,
                    maxLines = 1
                )
                Text(
                    story.level.code,
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    color = KotobaWood
                )
            }
            Text(
                story.text.take(80) + "...",
                fontFamily = Shippori,
                fontSize = 14.sp,
                color = KotobaInkSoft
            )
        }
    }
}