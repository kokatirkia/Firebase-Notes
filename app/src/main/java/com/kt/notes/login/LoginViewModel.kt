package com.kt.notes.login

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel @ViewModelInject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val isSignInSuccessful = MutableLiveData<Boolean>()
    private val loginResponseMessage = MutableLiveData<String>()
    fun isSignInSuccessful(): LiveData<Boolean> = isSignInSuccessful
    fun loginResponseMessage(): LiveData<String> = loginResponseMessage

    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.i("logCheck", "fields empty")
            loginResponseMessage.postValue("Fill Fields")
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isSignInSuccessful.postValue(true)
                Log.i("logCheck", "signInWithEmail:success")
            } else {
                isSignInSuccessful.postValue(false)
                loginResponseMessage.postValue(task.exception?.message)
                Log.i("logCheck", "signInWithEmail:failure", task.exception);
            }
        }
    }

}