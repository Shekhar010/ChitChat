package com.example.chitchat.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chitchat.data.repository.AuthenticationRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AuthViewModel @Inject constructor(
    val authRepo: AuthenticationRepo,
    val firebaseFirestore: FirebaseFirestore
) : ViewModel() {

    // state flow
    private var _currentUser = MutableStateFlow(FirebaseAuth.getInstance().currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    // login value
    private var _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    // registration value
    private var _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered


    // function
    fun login(email: String, password: String) {
        Log.d("login function", "function for login in viewModel called")
        viewModelScope.launch {
            val result = authRepo.signIn(email, password)
            Log.d("login function returned ", "$result")
            result.onSuccess {
                // set the current time to last login
                Log.d("login successful", "user logged in successfully and now setting last login")
                authRepo.setLastLogin()
                _isLoggedIn.value = true
            }
            result.onFailure {
                _isLoggedIn.value = false
            }

        }
    }

    // function logout
    fun logout() {
        viewModelScope.launch {
            authRepo.signOut()
            _isLoggedIn.value = false
        }
    }

    // function to register a user
    fun register(email: String, password: String, phone: String, name: String) {
        viewModelScope.launch {
            val result = authRepo.registerUser(email, password, name, phone)
            if (result != null) {
                Log.d("register function", "user registered successfully")
                _isRegistered.value = true
            }
        }
    }
}