package com.rspk.adminapp.firebase.impl

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rspk.adminapp.firebase.FireBaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireBaseStorageImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) : FireBaseStorage {

    override suspend fun uploadImage(image: Uri,type:String,fileName:String): Uri {
        val profilePicRef = storage.reference.child(GROCERY_IMAGES)
            .child(type)
            .child(fileName.trim())
        try {
            profilePicRef.putFile(image).await()

            val downloadUrl = profilePicRef.downloadUrl.await()
            return downloadUrl
        } catch (e: Exception) {
            Log.e(TAG, "Failed to upload profile pic: ${e.message}")
            return Uri.EMPTY
        }
    }


    override suspend fun deleteImage(type: String, fileName: String): Boolean =
        suspendCancellableCoroutine { continuation ->
            try {
                val filePath = "$GROCERY_IMAGES/${type.lowercase().trim()}/${fileName.trim()}"
                Log.d(TAG, "Attempting to delete file at path: $filePath")

                storage.reference.child(filePath).delete()
                    .addOnSuccessListener {
                        Log.d(TAG, "File deleted successfully.")
                        continuation.resume(true)
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to delete file: ${exception.message}")
                        continuation.resumeWithException(exception)
                    }
            } catch (ex: Exception) {
                Log.e(TAG, "Error occurred during delete operation: ${ex.message}")
                continuation.resumeWithException(ex)
            }
        }


    override suspend fun getImageFromOnline(context: Context,imageUri: String,fileName: String):Uri {
        try {
            val bytes = storage.getReferenceFromUrl(imageUri.toString())
                .getBytes(Long.MAX_VALUE)
                .await()

            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/")
            }

            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(bytes)
                    outputStream.flush()
                }
            }
            return uri ?: Uri.EMPTY
        } catch (ex: Exception) {
            Log.e(TAG, "Error occurred during delete operation: ${ex.message}")
            return Uri.EMPTY
        }
    }

    companion object {
        private const val TAG = "StorageRelatedError"
        private const val GROCERY_IMAGES = "grocery_images"
        private const val USER_UPLOADED_IMAGES = "user_uploaded_images"
        private const val USER_PROFILE_PICS = "user_profile_pics"
    }
}