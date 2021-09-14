package com.kt.notes.signup.model

data class SignUpState(
    var userName: String = "",
    var password: String = "",
    var repeatedPassword: String = "",
    val responseMessage: String? = null,
    var isLoading: Boolean = false,
    var navigateToNotes: Boolean = false
)
