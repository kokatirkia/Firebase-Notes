package com.kt.notes.signup.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kt.notes.signup.model.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _signUpState: MutableLiveData<SignUpState> = MutableLiveData(SignUpState())
    val signUpState: LiveData<SignUpState> = _signUpState

    fun onUserNameTextFieldValueChanged(newValue: String) {
        _signUpState.value = _signUpState.value!!.copy(userName = newValue)
    }

    fun onPasswordTextFieldValueChanged(newValue: String) {
        _signUpState.value = _signUpState.value!!.copy(password = newValue)
    }

    fun onRepeatedPasswordTextFieldValueChanged(newValue: String) {
        _signUpState.value = _signUpState.value!!.copy(repeatedPassword = newValue)
    }

    fun onNavigated() {
        _signUpState.value = _signUpState.value!!.copy(navigateToNotes = false)
    }

    fun onSignUpClicked() {
        _signUpState.value = _signUpState.value!!.copy(isLoading = true)

        if (
            _signUpState.value!!.userName.isEmpty() ||
            _signUpState.value!!.password.isEmpty() ||
            _signUpState.value!!.repeatedPassword.isEmpty()
        ) {
            _signUpState.value = _signUpState.value!!.copy(
                responseMessage = "Fill Fields",
                isLoading = false,
                navigateToNotes = false
            )
            return
        } else if (_signUpState.value!!.password != _signUpState.value!!.repeatedPassword) {
            _signUpState.value = _signUpState.value!!.copy(
                responseMessage = "password is not same",
                isLoading = false,
                navigateToNotes = false
            )
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(
            _signUpState.value!!.userName,
            _signUpState.value!!.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _signUpState.value = _signUpState.value!!.copy(
                    responseMessage = "User created",
                    isLoading = false,
                    navigateToNotes = true
                )
            } else {
                _signUpState.value = _signUpState.value!!.copy(
                    responseMessage = task.exception?.message ?: "Unknown failure",
                    isLoading = false,
                    navigateToNotes = false
                )
            }
        }
    }
}