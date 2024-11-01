package com.rspk.adminapp.firebase

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow


interface FireBaseStorage {

    suspend fun uploadImage(image: Uri, type: String, fileName: String): Uri
    suspend fun deleteImage(type: String, fileName: String):Boolean
    suspend fun getImageFromOnline(context: Context, imageUri: String, fileName: String): Uri
}