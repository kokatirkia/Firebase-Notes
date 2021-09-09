package com.kt.notes.login.model

data class LoginState(
    var isLoginSuccessful: Boolean = false,
    val responseMessage: String? = null,
    var isLoading: Boolean = false,
    var userName: String = "",
    var password: String = "",
)