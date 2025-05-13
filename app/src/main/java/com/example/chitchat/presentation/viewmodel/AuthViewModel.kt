package com.example.chitchat.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chitchat.data.repository.AuthenticationRepo
import com.example.chitchat.data.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AuthViewModel @Inject constructor(
    val authRepo: AuthenticationRepo,
    val profileRepo: ProfileRepository
) : ViewModel() {

    // state flow
    private val currentUser = FirebaseAuth.getInstance().currentUser

    // login value
    private var _isLoggedIn = MutableStateFlow(currentUser != null)
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
            val result = authRepo.registerUser(email, password)
            if(result != null) {
                Log.d("register function", "user registered successfully")
                _isRegistered.value = true
                enterUserDetails(name, phone, email, result)
            }
        }
    }

    // function to enter the user details such as name and phone

    fun enterUserDetails(name: String, phone: String, email: String, result: String?) {
        viewModelScope.launch {
            // store the other details such as name, phone number, email, created timestamp in the datastore
            profileRepo.storeUserDetails(name, phone, email, result)
        }
    }


}