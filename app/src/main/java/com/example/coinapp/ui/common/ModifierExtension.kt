package com.example.coinapp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
inline fun Modifier.clickableNoRipple(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit
): Modifier = this.composed {
    clickable(
        enabled = enabled,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}