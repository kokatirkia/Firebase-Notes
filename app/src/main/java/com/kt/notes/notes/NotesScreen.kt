package com.kt.notes.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kt.notes.R
import com.kt.notes.common.model.Note
import com.kt.notes.common.model.Screen
import com.kt.notes.common.presentation.SharedViewModel
import com.kt.notes.common.theme.NotesTheme
import java.util.*

@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel,
    sharedViewModel: SharedViewModel
) {
    val notes: List<Note> by notesViewModel.notes.observeAsState(ArrayList())

    NotesScreen(
        notes = notes,
        deleteNote = { notesViewModel.deleteNote(it) },
        setNote = { sharedViewModel.setNote(it) },
        navigateToAddNoteScreen = { navController.navigate(Screen.AddNote.route) },
        navigateToUpdateNoteScreen = { navController.navigate(Screen.UpdateNote.route) },
        navigateToLoginScreen = {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        },
        signOut = { notesViewModel.signOut() }
    )
}


@Composable
private fun NotesScreen(
    notes: List<Note>,
    setNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit,
    navigateToAddNoteScreen: () -> Unit,
    navigateToUpdateNoteScreen: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    signOut: () -> Unit,
) {
    NotesTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = Color.White,
                    title = { Text(text = stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = {
                            signOut()
                            navigateToLoginScreen()
                        }) {
                            Icon(
                                Icons.Filled.ExitToApp,
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = navigateToAddNoteScreen,
                    contentColor = Color.White,
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            },
            backgroundColor = Color.White,
            content = {
                NotesBodyContent(
                    notes = notes,
                    setNote = setNote,
                    deleteNote = deleteNote,
                    navigateToUpdateNoteScreen = navigateToUpdateNoteScreen
                )
            }
        )
    }
}

@Composable
fun NotesBodyContent(
    notes: List<Note>,
    setNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit,
    navigateToUpdateNoteScreen: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        notes.forEach {
            item {
                NoteItem(
                    note = it,
                    setNote = setNote,
                    deleteNote = deleteNote,
                    navigateToUpdateNoteScreen = navigateToUpdateNoteScreen
                )
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    setNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit,
    navigateToUpdateNoteScreen: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable(onClick = {
                setNote(note)
                navigateToUpdateNoteScreen()
            })
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = note.title, color = Color.White)
            Text(text = note.description, color = Color.White)
        }
        IconButton(onClick = { deleteNote(note) }) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}