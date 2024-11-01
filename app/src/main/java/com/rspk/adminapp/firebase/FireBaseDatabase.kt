package com.rspk.adminapp.firebase

import com.rspk.adminapp.model.ItemInsert
import kotlinx.coroutines.flow.Flow


interface FireBaseDatabase {

    suspend fun addOrUpdateItem(data: ItemInsert): Flow<Boolean>
    suspend fun getItemDetails(id:String): Flow<ItemInsert?>
    suspend fun deleteItem(id:String)
    suspend fun checkIsHeAdmin(): Boolean
    suspend fun requestAdmin():Boolean
    suspend fun isAlreadyRequested():Boolean
}