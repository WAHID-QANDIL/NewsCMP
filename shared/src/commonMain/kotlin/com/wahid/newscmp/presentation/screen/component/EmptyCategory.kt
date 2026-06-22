package com.wahid.newscmp.presentation.screen.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wahid.newscmp.utils.Colors.ColorTextMuted
import com.wahid.newscmp.utils.Colors.ColorTextPrimary

@Composable
fun EmptyCategory(category: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 200.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text("📭", fontSize = 32.sp)
            Text(
                text       = "No $category articles yet",
                color      = ColorTextPrimary,
                fontWeight = FontWeight.Medium,
                fontSize   = 15.sp,
            )
            Text(
                text  = "Check back later for updates",
                color = ColorTextMuted,
                fontSize = 13.sp,
            )
        }
    }
}