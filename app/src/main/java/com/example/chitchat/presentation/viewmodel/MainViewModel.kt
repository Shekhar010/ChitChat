package com.example.chitchat.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chitchat.data.repository.AuthenticationRepo
import com.example.chitchat.model.user.UserRes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainViewModel: ViewModel(){
    // this the main viewmodel contain all the information regarding the user , other things

    // store the userInformation fetched from the server
    private val _userInformation = mutableStateOf<UserRes?>(null)
    val userInformation = _userInformation.value



}