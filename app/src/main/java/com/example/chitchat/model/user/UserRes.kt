package com.example.chitchat.model.user

data class UserRes(
    val userId: String?,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String?,
    val gender: String?, // Optional: "Male", "Female", "Other"
    val dateOfBirth: String?, // Format: "yyyy-MM-dd"
    val accountCreatedAt: String?, // Timestamp
    val lastLoginAt: Long?, // Timestamp
    val isEmailVerified: Boolean?,
    val isPhoneVerified: Boolean?,
    val bio: String?,
    val socialLinks: Map<String, String>?
)
