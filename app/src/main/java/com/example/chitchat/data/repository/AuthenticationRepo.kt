package com.example.chitchat.data.repository


import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepo @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    // function to login
    suspend fun signIn(email: String, password: String): Result<Boolean> {
        // use the predefined fun
        try {
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
    suspend fun registerUser(email: String, password: String): String? {
        Log.d("firebase-auth", "register user function called")
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            // store the other details such as name, phone number, email, created timestamp in the datastore
            return result.user?.uid
        } catch (e: Exception) {
            Log.d("firebase-auth", "register user function failed")
            return null
        }
    }
}