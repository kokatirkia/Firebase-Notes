package com.kt.notes.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kt.notes.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _loginState: MutableLiveData<LoginState> = MutableLiveData(LoginState())
    val loginState: LiveData<LoginState> = _loginState

    fun onUserNameTextFieldValueChanged(newValue: String) {
        _loginState.value = _loginState.value!!.copy(userName = newValue)
    }

    fun onPasswordTextFieldValueChanged(newValue: String) {
        _loginState.value = _loginState.value!!.copy(password = newValue)
    }

    fun onNavigated() {
        _loginState.value = _loginState.value!!.copy(navigateToNotes = false)
    }

    fun onLoginClicked() {
        _loginState.value = _loginState.value!!.copy(isLoading = true)

        if (_loginState.value!!.userName.isEmpty() || _loginState.value!!.password.isEmpty()) {
            _loginState.value = _loginState.value!!.copy(
                responseMessage = "Fill Fields",
                isLoading = false,
                navigateToNotes = false
            )
            return
        }
        firebaseAuth.signInWithEmailAndPassword(
            _loginState.value!!.userName,
            _loginState.value!!.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _loginState.value = _loginState.value!!.copy(
                    responseMessage = "Login successful",
                    isLoading = false,
                    navigateToNotes = true
                )
            } else {
                _loginState.value = _loginState.value!!.copy(
                    responseMessage = task.exception?.message,
                    isLoading = false,
                    navigateToNotes = false
                )
            }
        }
    }
}