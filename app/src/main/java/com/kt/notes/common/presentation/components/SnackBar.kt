package com.kt.notes.common.presentation.components

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ShowSnackbar(message: String?, snackbarHostState: SnackbarHostState) {
    message?.let {
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = it)
        }
    }
}