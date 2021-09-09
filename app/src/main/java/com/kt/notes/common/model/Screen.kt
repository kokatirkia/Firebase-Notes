package com.kt.notes.common.model

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signUp")
    object Notes : Screen("notes")
    object AddNote : Screen("addNote")
    object UpdateNote : Screen("updateNote")
}