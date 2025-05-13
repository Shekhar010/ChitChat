package com.example.chitchat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chitchat.model.chat.Chats
import kotlinx.coroutines.flow.MutableStateFlow

class ChatViewModel : ViewModel() {

    private val _chatItemsList = MutableStateFlow<List<Chats>?>(null)
    val chatItemsList = _chatItemsList.value

    // get the chat items list
}