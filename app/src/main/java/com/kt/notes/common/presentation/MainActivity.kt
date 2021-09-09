package com.kt.notes.common.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kt.notes.R
import com.kt.notes.addnote.presentation.AddNoteScreen
import com.kt.notes.addnote.presentation.AddNoteViewModel
import com.kt.notes.common.model.Screen
import com.kt.notes.login.presentation.LoginScreen
import com.kt.notes.login.presentation.LoginViewModel
import com.kt.notes.notes.NotesScreen
import com.kt.notes.notes.NotesViewModel
import com.kt.notes.signup.presentation.SignUpScreen
import com.kt.notes.signup.presentation.SignUpViewModel
import com.kt.notes.updatenote.presentation.UpdateNoteScreen
import com.kt.notes.updatenote.presentation.UpdateNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Notes)
        super.onCreate(savedInstanceState)

        val sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        val startDestination =
            if (sharedViewModel.isUserSignedIn()) Screen.Notes.route else Screen.Login.route

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = Screen.Login.route) {
                    val loginViewModel = hiltViewModel<LoginViewModel>()
                    LoginScreen(
                        navController = navController,
                        loginViewModel = loginViewModel
                    )
                }
                composable(route = Screen.SignUp.route) {
                    val signUpViewModel = hiltViewModel<SignUpViewModel>()
                    SignUpScreen(
                        navController = navController,
                        signUpViewModel = signUpViewModel
                    )
                }
                composable(route = Screen.Notes.route) {
                    val notesViewModel = hiltViewModel<NotesViewModel>()
                    NotesScreen(
                        navController = navController,
                        notesViewModel = notesViewModel,
                        sharedViewModel = sharedViewModel
                    )
                }
                composable(route = Screen.AddNote.route) {
                    val addNoteViewModel = hiltViewModel<AddNoteViewModel>()
                    AddNoteScreen(
                        navController = navController,
                        addNoteViewModel = addNoteViewModel
                    )
                }
                composable(route = Screen.UpdateNote.route) {
                    val updateNoteViewModel = hiltViewModel<UpdateNoteViewModel>()
                    UpdateNoteScreen(
                        navController = navController,
                        updateNoteViewModel = updateNoteViewModel,
                        sharedViewModel = sharedViewModel
                    )
                }
            }
        }
    }
}