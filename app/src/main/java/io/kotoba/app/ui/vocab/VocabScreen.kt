package io.kotoba.app.ui.vocab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import io.kotoba.app.domain.model.VocabProgress
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaPaper
import io.kotoba.app.ui.theme.Shippori
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabScreen(
    vm: VocabViewModel,
    modifier: Modifier = Modifier
) {
    val state by vm.ui.collectAsState()
    Scaffold(
        containerColor = KotobaPaper,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "言葉 • Vocab",
                        fontFamily = Shippori,
                        fontWeight = FontWeight.Light,
                        fontSize = 22.sp,
                        color = KotobaInk
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KotobaPaper)
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VocabTab.entries.forEach { tab ->
                    FilterChip(
                        selected = state.activeTab == tab,
                        onClick = { vm.setTab(tab) },
                        label = { Text(when (tab) { VocabTab.SRS_FLASHCARDS -> "SRS Flashcards"; VocabTab.WORD_LIST -> "Word List" }) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KotobaInk,
                            labelColor = KotobaPaper,
                            selectedLabelColor = KotobaPaper
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
                    )
                }
            }
            when (state.activeTab) {
                VocabTab.SRS_FLASHCARDS -> VocabSrsTab(state, vm)
                VocabTab.WORD_LIST -> VocabWordListView(
                    items = state.items,
                    query = state.query,
                    showReading = state.showReading,
                    showMeaning = state.showMeaning,
                    onQueryChange = vm::setQuery,
                    onToggleReading = vm::toggleReading,
                    onToggleMeaning = vm::toggleMeaning
                )
            }
        }
    }
}

@Composable
private fun VocabSrsTab(state: VocabUiState, vm: VocabViewModel) {
    val progress = state.due.getOrNull(state.currentIndex)
    val vocab = progress?.let { p -> state.items.firstOrNull { it.id == p.vocabId } }
    if (vocab != null && progress != null) {
        VocabSrsCardView(
            vocab = vocab,
            progress = progress,
            showReading = state.showReading,
            showMeaning = state.showMeaning,
            onRated = vm::rateCard
        )
        SpacerRow()
        Sm2RatingRow(onRated = vm::rateCard)
    } else {
        EmptyCard("No due cards for ${state.level.code} yet.")
    }
}

@Composable
private fun EmptyCard(text: String) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = KotobaPaper)
    ) {
        Text(
            text,
            modifier = Modifier.padding(24.dp),
            fontFamily = Shippori,
            color = KotobaInk
        )
    }
}

@Composable
private fun SpacerRow() {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
}