package com.kt.notes.updatenote.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kt.notes.R
import com.kt.notes.common.presentation.SharedViewModel
import com.kt.notes.common.presentation.components.NoteTextField
import com.kt.notes.common.presentation.components.SaveNoteButton
import com.kt.notes.common.theme.NotesTheme
import com.kt.notes.updatenote.model.UpdateNoteState

@Composable
fun UpdateNoteScreen(
    navController: NavController,
    updateNoteViewModel: UpdateNoteViewModel,
    sharedViewModel: SharedViewModel
) {
    val updateNoteState: UpdateNoteState by updateNoteViewModel.updateNoteState.observeAsState(
        UpdateNoteState()
    )

    updateNoteViewModel.setInitialTextFieldValues(sharedViewModel.getNote())

    UpdateNoteScreen(
        popBackStack = { navController.popBackStack() },
        updateNoteState = updateNoteState,
        onTitleChange = { updateNoteViewModel.onTitleTextFieldValueChanged(it) },
        onDescriptionChange = { updateNoteViewModel.onDescriptionTextFieldValueChanged(it) },
        updateNote = { updateNoteViewModel.updateNote(sharedViewModel.getNote().id) },
        deleteNote = { updateNoteViewModel.deleteNote(sharedViewModel.getNote().id) },
    )
}

@Composable
private fun UpdateNoteScreen(
    popBackStack: () -> Unit,
    updateNoteState: UpdateNoteState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    updateNote: () -> Unit,
    deleteNote: () -> Unit
) {
    NotesTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = Color.White,
                    title = { Text(text = stringResource(R.string.update_note)) },
                    navigationIcon = {
                        IconButton(onClick = popBackStack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            updateNote()
                            popBackStack()
                        }) {
                            Icon(Icons.Filled.Done, tint = Color.White, contentDescription = null)
                        }
                        IconButton(onClick = {
                            deleteNote()
                            popBackStack()
                        }) {
                            Icon(Icons.Filled.Delete, tint = Color.White, contentDescription = null)
                        }
                    }
                )
            },
            backgroundColor = Color.White,
            content = {
                UpdateNoteBodyContent(
                    popBackStack = popBackStack,
                    updateNoteState = updateNoteState,
                    onTitleChange = onTitleChange,
                    onDescriptionChange = onDescriptionChange,
                    updateNote = updateNote
                )
            }
        )
    }
}

@Composable
fun UpdateNoteBodyContent(
    popBackStack: () -> Unit,
    updateNoteState: UpdateNoteState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    updateNote: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteTextField(
                placeHolder = stringResource(R.string.title),
                value = updateNoteState.title,
                onValueChange = onTitleChange,
                singleLine = true
            )
            Divider(thickness = 10.dp)
            NoteTextField(
                placeHolder = stringResource(R.string.description),
                value = updateNoteState.description,
                onValueChange = onDescriptionChange,
                singleLine = false
            )
        }
        SaveNoteButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.primary),
            onCLick = {
                updateNote()
                popBackStack()
            }
        )
    }
}
