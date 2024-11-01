package com.rspk.adminapp.firebase.impl

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rspk.adminapp.firebase.FireBaseAccount
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireBaseAccountImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FireBaseAccount {

    override val currentUser = auth.uid

    override val hasUser :Boolean = auth.currentUser != null

    override suspend fun signInWithEmailAccount(email: String, password: String):Boolean
        = suspendCoroutine { continuation ->
            auth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }

        }

    override suspend fun forgotPassword(email: String):Boolean {
        return suspendCancellableCoroutine { continuation ->
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun signOut(){
        try {
            if(auth.currentUser!!.isAnonymous)
                auth.currentUser!!.delete().await()
            else
                auth.signOut()
        }catch (ex:Exception){
            Log.e("TesTingProgress", "Error occurred: ${ex.message}", ex)
        }
    }
}