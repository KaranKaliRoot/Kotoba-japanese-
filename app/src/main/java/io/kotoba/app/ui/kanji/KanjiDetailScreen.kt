package io.kotoba.app.ui.kanji

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.domain.model.Kanji
import io.kotoba.app.domain.model.KanjiCompound
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.Shippori

@Composable
fun KanjiDetailScreen(
    kanji: Kanji,
    compounds: List<KanjiCompound>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            kanji.character,
            fontFamily = Shippori,
            fontSize = 90.sp,
            fontWeight = FontWeight.Light,
            color = KotobaInk
        )
        Text(kanji.meaning, fontSize = 20.sp, color = KotobaInk.copy(0.7f))

        KanjiDrawCanvas(
            kanji = kanji,
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Onyomi", fontWeight = FontWeight.Bold, color = KotobaInk.copy(0.6f))
            Text(kanji.onyomi, fontSize = 18.sp, color = KotobaInk)

            Text("Kunyomi", fontWeight = FontWeight.Bold, color = KotobaInk.copy(0.6f))
            Text(kanji.kunyomi, fontSize = 18.sp, color = KotobaInk)

            Text("Compounds", fontWeight = FontWeight.Bold, color = KotobaInk.copy(0.6f))
            compounds.forEach { c ->
                Text("${c.character} (${c.reading}) - ${c.meaning}", fontSize = 16.sp, color = KotobaInk)
            }
        }
    }
}