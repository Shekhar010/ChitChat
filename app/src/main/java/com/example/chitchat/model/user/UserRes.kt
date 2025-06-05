package com.example.chitchat.model.user

data class UserRes(
    val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val gender: String = "",
    val dateOfBirth: String = "",
    val bio: String = "",
    val profileImageUrl: String = "",
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val accountCreatedAt: Long = 0L,
    val lastLoginAt: Long = 0L,
    val socialLinks: Map<String, String> = emptyMap()
)
