package com.example.chitchat.data.repository

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.chitchat.model.user.UserRes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume

// repository to fetch the user details
@RequiresApi(Build.VERSION_CODES.O)
class ProfileRepository @Inject constructor(
    firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    // in the current user add the details at time of registration
    val userId = firebaseAuth.currentUser?.uid

    // user document reference
    private val userDocumentRef = userId?.let { firebaseFirestore.collection("users").document(it) }

    // get the user details from the firestore
    suspend fun getUserDetails(): Flow<UserRes?> = flow {
        try {
            val documentSnapshot = userDocumentRef?.get()?.await()
            if (documentSnapshot?.exists() == true) {
                emit(documentSnapshot.toObject(UserRes::class.java))
            } else {
                emit(null)
            }
        } catch (e: Exception){
            Log.e("FirestoreError", "Failed to fetch user: ${e.message}")
        }
//        return try {
//            val documentSnapshot = userDocumentRef?.get()?.await()
//            if (documentSnapshot?.exists() == true) {
//                documentSnapshot.toObject(UserRes::class.java)
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            Log.e("FirestoreError", "Failed to fetch user: ${e.message}")
//            null
//        }
    }



    // upload image to the cloudinary
    suspend fun uploadImageToCloud(imageUri: Uri?): Result<String?> = suspendCancellableCoroutine{ continuation ->
        MediaManager.get().upload(imageUri)
            .unsigned("chat_preset")
            .option("folder", "profilePics")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) {
                    println("Upload started: $requestId")
                }

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                    println("Upload progress: ${bytes.toFloat() / totalBytes * 100}%")
                }

                override fun onSuccess(requestId: String, resultData: Map<Any?, Any?>) {
                    val imageUrl = resultData["url"] as? String
                    Log.d("image-url", "$imageUrl")
                    continuation.resume(Result.success(imageUrl))
                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    println("Upload error: ${error.description}")
                    continuation.resume(Result.failure(Exception("Upload failed: ${error.description}")))
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {
                    println("Upload rescheduled: ${error.description}")
                }
            })
            .dispatch()
    }

    // function to update the profile image in firebase
    suspend fun updateProfileImage(imageUrl: String){
        try{
            val userData = mapOf(
                "profileImageUrl" to imageUrl
            )
            // update it
            userDocumentRef?.update(userData)?.await()
        } catch (e: Exception){
            Log.e("FirestoreError", "Failed to update profile image: ${e.message}")
        }
    }


}
