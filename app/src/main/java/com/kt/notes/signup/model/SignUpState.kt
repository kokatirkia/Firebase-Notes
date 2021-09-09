package com.kt.notes.signup.model

data class SignUpState(
    var isSignUpSuccessful: Boolean = false,
    val responseMessage: String? = null,
    var isLoading: Boolean = false,
    var userName: String = "",
    var password: String = "",
    var repeatedPassword: String = "",
)
