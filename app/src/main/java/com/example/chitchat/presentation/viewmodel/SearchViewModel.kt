package com.example.chitchat.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
): ViewModel() {

    // for searched number
    private val _searchedNumber = mutableStateOf<String>("")
    val searchedNumber = _searchedNumber.value

    // function to change the searched number
    fun editSearchNumber(number: String){
        _searchedNumber.value = number
    }
}