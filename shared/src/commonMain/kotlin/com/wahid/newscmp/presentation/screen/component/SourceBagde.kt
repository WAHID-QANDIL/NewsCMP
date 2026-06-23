package com.wahid.newscmp.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wahid.newscmp.utils.Colors.ColorAccent
import com.wahid.newscmp.utils.Colors.ColorTextMuted

@Composable
fun SourceBadge(source: String, time: String) {
    Row(
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(ColorAccent),
        )
        Text(source, color = ColorAccent,    fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Text("·",    color = ColorTextMuted, fontSize = 11.sp)
        Text(time,   color = ColorTextMuted, fontSize = 11.sp)
    }
}