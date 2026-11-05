package io.kotoba.app.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.Shippori

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    vm: DashboardViewModel,
    onNavigate: (String) -> Unit
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        containerColor = KotobaPaper,
        topBar = {
            TopAppBar(
                title = {
                    Text("言葉 • kotoba.", fontFamily = Shippori, fontWeight = FontWeight.Light, fontSize = 22.sp, color = KotobaInk)
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        JlptLevel.entries.forEach { level ->
                            FilterChip(
                                selected = state.activeLevel == level,
                                onClick = { vm.setActiveLevel(level) },
                                label = { Text(level.code, fontFamily = Shippori, fontSize = 12.sp) },
                                shape = RoundedCornerShape(50),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = KotobaInk,
                                    labelColor = KotobaPaper,
                                    selectedLabelColor = KotobaPaper
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = state.activeLevel == level,
                                    borderColor = KotobaInk,
                                    selectedBorderColor = KotobaInk,
                                    disabledBorderColor = KotobaInk,
                                    disabledSelectedBorderColor = KotobaInk
                                ),
                                modifier = Modifier.width(44.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KotobaPaper)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KotobaBentoCard(
                    title = "Due SRS",
                    value = "${state.dueCards}",
                    tint = io.kotoba.app.ui.theme.KotobaClay,
                    modifier = Modifier.weight(1f)
                )
                KotobaBentoCard(
                    title = "Grammar",
                    value = "${state.learnedGrammar}/${state.totalGrammar}",
                    tint = io.kotoba.app.ui.theme.KotobaSand,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KotobaBentoCard(
                    title = "Kanji",
                    value = "${state.learnedKanji}",
                    tint = io.kotoba.app.ui.theme.KotobaButter,
                    modifier = Modifier.weight(1f)
                )
                KotobaBentoCard(
                    title = "Stories",
                    value = "${state.readStories}",
                    tint = io.kotoba.app.ui.theme.KotobaClay,
                    modifier = Modifier.weight(1f)
                )
            }
            ContinueHeroCard(onClick = { onNavigate("vocab") })
        }
    }
}