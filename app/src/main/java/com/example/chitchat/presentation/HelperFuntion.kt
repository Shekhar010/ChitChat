package com.example.chitchat.presentation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatMillisToDate(millis: Long): String {
    val date = Date(millis)
    val format = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return format.format(date)
}