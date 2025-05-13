package com.example.chitchat.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.chitchat.model.user.UserRes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// repository to fetch the user details
@RequiresApi(Build.VERSION_CODES.O)
class ProfileRepository @Inject constructor(
    firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    // in the current user add the details at time of registration
    val userId = firebaseAuth.currentUser?.uid

    // time
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val Time = currentTime.format(formatter)

    // store the information in the database
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun storeUserDetails(
        name: String, phone: String, email: String, result: String?
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            val user = UserRes(
                userId = result,
                fullName = name,
                email = email,
                phoneNumber = phone,
                accountCreatedAt = time,
                profileImageUrl = null,
                gender = null,
                dateOfBirth = null,
                lastLoginAt = null,
                isEmailVerified = null,
                isPhoneVerified = null,
                bio = null,
                socialLinks = null
            )

            result?.let {
                firebaseFirestore.collection("Users").document(it).set(user).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Failed to store user details", e)
            Result.failure(e)
        }
    }

}