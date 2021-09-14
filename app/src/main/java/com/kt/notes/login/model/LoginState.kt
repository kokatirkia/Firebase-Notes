package com.kt.notes.login.model

data class LoginState(
    var userName: String = "",
    var password: String = "",
    val responseMessage: String? = null,
    var isLoading: Boolean = false,
    var navigateToNotes: Boolean = false
)