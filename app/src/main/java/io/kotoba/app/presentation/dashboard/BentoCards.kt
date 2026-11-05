package io.kotoba.app.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaMoss
import io.kotoba.app.ui.theme.Shippori

private val BentoShape = RoundedCornerShape(24.dp)

@Composable
fun KotobaBentoCard(
    title: String,
    value: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(BentoShape),
        colors = CardDefaults.cardColors(containerColor = tint),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = KotobaInk, fontFamily = Shippori, fontSize = 14.sp)
            Text(value, color = KotobaInk, fontFamily = Shippori, fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
        }
    }
}

@Composable
fun ContinueHeroCard(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(112.dp)
            .clip(BentoShape),
        colors = CardDefaults.cardColors(containerColor = KotobaMoss),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Continue Learning", color = KotobaInk, fontFamily = Shippori, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Next card in your review queue", color = KotobaInk.copy(alpha = 0.6f), fontFamily = Shippori, fontSize = 13.sp)
            }
            Card(
                onClick = onClick,
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(containerColor = KotobaInk),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Text("↗", modifier = Modifier.size(36.dp), color = Color.White, fontFamily = Shippori, fontSize = 20.sp)
            }
        }
    }
}