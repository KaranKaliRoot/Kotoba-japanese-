package io.kotoba.app.ui.grammar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.Grammar
import io.kotoba.app.ui.grammar.GrammarMode.FUNCTIONAL
import io.kotoba.app.ui.grammar.GrammarMode.WEEKLY
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaInkSoft
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.KotobaWood
import io.kotoba.app.ui.theme.Shippori

@Composable
fun GrammarListScreen(
    vm: GrammarViewModel,
    onSelected: (Grammar) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by vm.ui.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = state.mode == WEEKLY,
                onClick = { vm.setMode(WEEKLY) },
                label = { Text("Weekly") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = KotobaInk,
                    labelColor = KotobaPaper,
                    selectedLabelColor = KotobaPaper
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            )
            FilterChip(
                selected = state.mode == FUNCTIONAL,
                onClick = { vm.setMode(FUNCTIONAL) },
                label = { Text("Nuance") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = KotobaInk,
                    labelColor = KotobaPaper,
                    selectedLabelColor = KotobaPaper
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            )
        }
        val groups = vm.groupedItems()
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            groups.forEach { (title, list) ->
                item {
                    Text(
                        title,
                        fontFamily = Shippori,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = KotobaWood
                    )
                }
                items(list) { g ->
                    GrammarCard(g) { onSelected(g) }
                }
            }
        }
    }
}

@Composable
private fun GrammarCard(g: Grammar, onClick: (Grammar) -> Unit) {
    Card(
        onClick = { onClick(g) },
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
                    g.pattern,
                    fontFamily = Shippori,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = KotobaInk
                )
                Text(
                    g.level.code,
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    color = KotobaWood
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                g.meaning,
                fontFamily = Shippori,
                fontSize = 14.sp,
                color = KotobaInkSoft
            )
        }
    }
}