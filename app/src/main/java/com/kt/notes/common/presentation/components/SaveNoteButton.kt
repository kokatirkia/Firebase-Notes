package com.kt.notes.common.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SaveNoteButton(
    onCLick: () -> Unit,
    modifier: Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onCLick
    ) {
        Icon(
            Icons.Filled.Done,
            modifier = Modifier.size(40.dp),
            tint = Color.White,
            contentDescription = null
        )
    }
}