package com.example.chitchat.presentation.viewmodel

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.example.chitchat.data.repository.ProfileRepository
import com.example.chitchat.model.user.UserRes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.annotation.meta.When
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    val profileRepo: ProfileRepository
) : ViewModel() {
    // this the main viewmodel contain all the information regarding the user , other things

    // current user
    private var _currentUser = MutableStateFlow(firebaseAuth.currentUser?.uid)
    val currentUser: StateFlow<String?> = _currentUser

    // store the userInformation fetched from the server
    private val _user = MutableStateFlow<UserRes>(UserRes())
    val user = _user.asStateFlow()


    // function to get current user details
    @RequiresApi(Build.VERSION_CODES.O)
    fun getUserDetails() {
        viewModelScope.launch {
            profileRepo.getUserDetails().collect{ result->
                when(result){
                    is UserRes -> {
                        Log.d("user details from flow", "$result")
                        _user.value = result
                    }
                }
            }
        }
    }

    // profile picture upload
    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadProfilePicture(imageUri: Uri?) {
        viewModelScope.launch {
            Log.d("image-uri", "$imageUri")
            val result = profileRepo.uploadImageToCloud(imageUri)
            Log.d("image-result", "$result")

            result.onSuccess {
                Log.d("image-url 1.", "$it")
                // update the image url in the firestore
                if (it != null) {
                    profileRepo.updateProfileImage(it)
                }
            }
            getUserDetails()
        }
    }
}