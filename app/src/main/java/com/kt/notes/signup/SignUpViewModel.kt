package com.kt.notes.signup

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel @ViewModelInject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val isRegistrationSuccess = MutableLiveData<Boolean>()
    private val registrationResponseMessage = MutableLiveData<String>()
    fun isRegistrationSuccess(): LiveData<Boolean> = isRegistrationSuccess
    fun registrationResponseMessage(): LiveData<String> = registrationResponseMessage

    fun createUser(email: String, password: String, repeatedPassword: String) {
        if (email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            Log.d("logCheck", "fields empty")
            registrationResponseMessage.postValue("Fill Fields")
            return
        } else if (password != repeatedPassword) {
            Log.d("logCheck", "password is not same")
            registrationResponseMessage.postValue("password is not same")
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isRegistrationSuccess.postValue(true)
                Log.d("logCheck", "createUserWithEmail:success")
            } else {
                isRegistrationSuccess.postValue(false)
                registrationResponseMessage.postValue(task.exception?.message)
                Log.w("logCheck", "createUserWithEmail:failure", task.exception)
            }
        }
    }
}