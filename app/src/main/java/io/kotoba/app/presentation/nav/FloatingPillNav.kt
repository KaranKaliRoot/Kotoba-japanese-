package io.kotoba.app.presentation.nav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kotoba.app.ui.theme.KotobaInk
import io.kotoba.app.ui.theme.KotobaPaper

@Composable
fun FloatingPillNav(
    currentRoute: String?,
    onSelected: (KotobaTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(28.dp)
    androidx.compose.material3.Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .height(56.dp),
        shape = shape,
        color = KotobaInk,
        shadowElevation = 8.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomTabs.forEach { tab ->
                val selected = tab.route == currentRoute
                FloatingActionButton(
                    onClick = { onSelected(tab) },
                    modifier = Modifier.height(40.dp),
                    containerColor = if (selected) KotobaPaper else KotobaInk,
                    contentColor = if (selected) KotobaInk else KotobaPaper,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        tab.icon,
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                        color = if (selected) KotobaInk else KotobaPaper,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
            }
        }
    }
}