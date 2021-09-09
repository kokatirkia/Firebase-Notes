package com.kt.notes.common.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val colorPalette = darkColors(
    primary = blue_500,
    primaryVariant = blue_700,
    background = blue_500,
)

@Composable
fun NotesTheme(content: @Composable () -> Unit) {
    val colors = colorPalette
    MaterialTheme(
        colors = colors,
        content = content
    )
}