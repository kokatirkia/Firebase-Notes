package com.kt.notes.addnote.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.kt.notes.addnote.model.AddNoteState
import com.kt.notes.common.presentation.components.NoteTextField
import com.kt.notes.common.presentation.components.SaveNoteButton
import com.kt.notes.common.theme.NotesTheme

@Composable
fun AddNoteScreen(
    navController: NavController,
    addNoteViewModel: AddNoteViewModel
) {
    val addNoteState: AddNoteState by addNoteViewModel.addNoteState.observeAsState(AddNoteState())

    AddNoteScreen(
        popBackStack = { navController.popBackStack() },
        addNoteState = addNoteState,
        onTitleChange = { addNoteViewModel.onTitleTextFieldValueChanged(it) },
        onDescriptionChange = { addNoteViewModel.onDescriptionTextFieldValueChanged(it) },
        addNote = { addNoteViewModel.addNote() }
    )
}

@Composable
private fun AddNoteScreen(
    popBackStack: () -> Unit,
    addNoteState: AddNoteState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    addNote: () -> Unit
) {
    NotesTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = Color.White,
                    title = { Text(text = stringResource(R.string.add_note)) },
                    navigationIcon = {
                        IconButton(onClick = popBackStack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            addNote()
                            popBackStack()
                        }) {
                            Icon(Icons.Filled.Done, tint = Color.White, contentDescription = null)
                        }
                    }
                )
            },
            backgroundColor = Color.White,
            content = {
                AddNoteBodyContent(
                    popBackStack = popBackStack,
                    addNoteState = addNoteState,
                    onTitleChange = onTitleChange,
                    onDescriptionChange = onDescriptionChange,
                    addNote = addNote
                )
            }
        )
    }

}

@Composable
fun AddNoteBodyContent(
    popBackStack: () -> Unit,
    addNoteState: AddNoteState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    addNote: () -> Unit
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
                value = addNoteState.title,
                onValueChange = onTitleChange,
                singleLine = true
            )
            Divider(thickness = 10.dp)
            NoteTextField(
                placeHolder = stringResource(R.string.description),
                value = addNoteState.description,
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
                addNote()
                popBackStack()
            }
        )
    }
}
