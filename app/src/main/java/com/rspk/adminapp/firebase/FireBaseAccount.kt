package com.rspk.adminapp.firebase


interface FireBaseAccount {
    val hasUser: Boolean
    val currentUser: String?
    suspend fun signInWithEmailAccount(email: String, password: String):Boolean
    suspend fun forgotPassword(email: String):Boolean

    suspend fun signOut()
}