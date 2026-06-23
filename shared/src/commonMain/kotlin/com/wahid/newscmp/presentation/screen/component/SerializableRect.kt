package com.wahid.newscmp.presentation.screen.component

import androidx.compose.ui.geometry.Rect
import kotlinx.serialization.Serializable

@Serializable
data class SerializableRect(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
)

fun Rect.toSerializableRect() = SerializableRect(
    left = left,
    top = top,
    right = right,
    bottom = bottom
)

fun SerializableRect.toRect() = Rect(
    left = left,
    top = top,
    right = right,
    bottom = bottom
)