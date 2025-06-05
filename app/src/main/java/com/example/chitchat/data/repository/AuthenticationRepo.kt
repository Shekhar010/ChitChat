package com.example.chitchat.data.repository


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.chitchat.model.user.UserRes
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepo @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    // function to login
    suspend fun signIn(email: String, password: String): Result<Boolean> {
        // use the predefined fun
        try {
            val userData = mapOf(
                "lastLoginAt" to System.currentTimeMillis()
            )
            Log.d("firebase-auth", "sign in function called")
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return Result.success(true)
        } catch (e: Exception) {
            Log.d("firebase-auth", "sign in function failed")
            return Result.failure(e)
        }
    }

    // function for sign-out
    fun signOut() {
        Log.d("firebase-auth", "sign out function called")
        firebaseAuth.signOut()
    }

    // function to register a user
    suspend fun registerUser(email: String, password: String, name: String, phone: String): Result<String> {
        Log.d("firebase-auth", "register user function called")
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID is null")
            Log.d("user id", userId)

            // map for social links
            val map = mapOf<String?, String?>()

            // prepare the data
            val userData = hashMapOf(
                "userId" to userId,
                "fullName" to name,
                "email" to email,
                "phone" to phone,
                "profileImageUrl" to "",
                "gender" to "",
                "dateOfBirth" to "",
                "accountCreatedAt" to System.currentTimeMillis(),
                "lastLoginAt" to null,
                "isEmailVerified" to false,
                "isPhoneVerified" to false,
                "bio" to "",
                "socialLinks" to map
            )

            Log.d("user data", userData.toString())

            // Save to Firestore under users/{uid}
            firebaseFirestore.collection("users").document(userId).set(userData).await()

            Log.d("firebase-auth", "User registered and data stored for UID: $userId")
            Result.success(userId)

        } catch (e: Exception) {
            Log.d("firebase-auth", "register user function failed $e")
            Result.failure(e)
        }
    }

    // set last login
    suspend fun setLastLogin(){
        try {
            val userData = mapOf(
                "lastLoginAt" to System.currentTimeMillis()
            )
            Log.d("firebase-last-login-time-update", "function called to update time with user id ${firebaseAuth.currentUser?.uid}")
            firebaseAuth.currentUser?.uid?.let { firebaseFirestore.collection("users").document(it).update(userData).await() }
        } catch (e: Exception){
            Log.d("error", "during setting last login time")
        }
    }

}