package com.rspk.adminapp.firebase.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Source
import com.rspk.adminapp.common.snackbar.SnackBarManager
import com.rspk.adminapp.firebase.FireBaseDatabase
import com.rspk.adminapp.model.ItemInsert
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireBaseDatabaseImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FireBaseDatabase {

    override suspend fun getItemDetails(id:String): Flow<ItemInsert?> =
        callbackFlow {
            try {
                val ref = fireStore.collection(ADMIN)
                    .document(ADMIN_DOCUMENT_ID_FOR_USER_GROCERY_LIST)
                    .get()
                    .await()
                    .data
                    ?.get(id) as Map<String,String>

                val item = ItemInsert(
                    id = id,
                    description = ref["description"] as String,
                    image = ref["image"] as String,
                    avgPrice = ref["avg_price"] as String,
                    quantityType = ref["quantity_type"] as String,
                    starCount = ref["star_count"] as String,
                    subType = ref["sub_type"] as String,
                    type = ref["type"] as String,
                    searchKeywords = ref["search_keywords"] as String
                    )
                trySend(item)
            }catch (e:Exception){
                Log.d(TAG, "Error While Adding Item To FireStore $e")
                trySend(null)
            }
            awaitClose()
        }

    override suspend fun addOrUpdateItem(data: ItemInsert):Flow<Boolean> =
        callbackFlow {
            try {
                fireStore.collection(ADMIN)
                    .document(ADMIN_DOCUMENT_ID_FOR_USER_GROCERY_LIST)
                    .update(groceryItemToMap(data))
                    .addOnSuccessListener {
                        trySend(true)
                        SnackBarManager.showMessage("Uploaded SuccessFully")
                        trySend(false)
                    }
                    .await()

            }catch (e:Exception){
                Log.d(TAG, "Error While Adding Item To FireStore $e")
                trySend(false)
            }
            awaitClose()
        }

    //^^^^^^^^^^^^^^ Above Function Util Private Function ^^^^^^^^^^^^^^\\
    private fun groceryItemToMap(item: ItemInsert): Map<String, Map<String, String>> {

        val itemDataMap = mapOf(
            "description" to item.description,
            "image" to item.image,
            "avg_price" to item.avgPrice,
            "quantity_type" to item.quantityType,
            "star_count" to item.starCount,
            "quantity_type" to item.quantityType,
            "sub_type" to item.subType,
            "type" to item.type,
            "search_keywords" to item.searchKeywords,
            "uploaded_by" to item.userId
        )

        return mapOf(item.id to itemDataMap)
    }


    override suspend fun checkIsHeAdmin():Boolean =
        suspendCoroutine { continuation ->
           fireStore
                .collection(USERS_INFO)
                .document(auth.currentUser?.uid!!)
                .get()
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(it.getBoolean("admin") ?: false))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }

    override suspend fun deleteItem(id:String) {
        try {
            fireStore.collection(ADMIN)
                .document(ADMIN_DOCUMENT_ID_FOR_USER_GROCERY_LIST)
                .update(mapOf(id to FieldValue.delete()))
                .addOnSuccessListener {
                    SnackBarManager.showMessage("Deleted SuccessFully")
                }
                .await()
        } catch (e: Exception) {
            Log.d(TAG, "Error While Adding Item To FireStore $e")
        }
    }

    override suspend fun isAlreadyRequested(): Boolean =
        suspendCoroutine { continuation ->
            try {
                fireStore.collection(REQUESTS)
                    .get()
                    .addOnSuccessListener {
                        continuation.resume(
                            if(it.isEmpty) false else it.documents.any { it.id == auth.currentUser?.uid }
                        )
                    }
                    .addOnFailureListener{
                        continuation.resume(false)
                    }
            }catch (ex:Exception){
                continuation.resumeWithException(ex)
                Log.d(TAG, "Error While Adding Item To FireStore $ex")
            }
        }


    override suspend fun requestAdmin(): Boolean =
        suspendCoroutine { continuation ->
            try {
                fireStore.collection(REQUESTS)
                    .document(auth.currentUser?.uid!!)
                    .set(mapOf(
                        "request" to "requested",
                        "email" to auth.currentUser?.email
                    ))
                    .addOnSuccessListener {
                        SnackBarManager.showMessage("Request Sent Successfully")
                        continuation.resume(true)
                    }
                    .addOnFailureListener{
                        continuation.resume(false)
                    }
            }catch (ex:Exception){
                continuation.resumeWithException(ex)
                Log.d(TAG, "Error While Adding Item To FireStore $ex")
            }
        }

    companion object {
        private const val TAG = "FireStoreRelatedError"
        private const val ADMIN = "admin"
        private const val ADMIN_DOCUMENT_ID_FOR_USER_GROCERY_LIST = "list_of_groceries"
        private const val USERS_INFO = "users_info"
        private const val REQUESTS = "requests"
    }
}


