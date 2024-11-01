package com.rspk.adminapp.compose.home

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.rspk.adminapp.compose.MainViewModel
import com.rspk.adminapp.constants.CurrentButtonLoading
import com.rspk.adminapp.firebase.FireBaseAccount
import com.rspk.adminapp.firebase.FireBaseDatabase
import com.rspk.adminapp.firebase.FireBaseStorage
import com.rspk.adminapp.model.ItemInsert
import com.rspk.adminapp.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val fireBaseAccount: FireBaseAccount,
    private val fireBaseStorage: FireBaseStorage,
    private val fireBaseDatabase: FireBaseDatabase
):MainViewModel() {

    var id by mutableStateOf("")
    var description by mutableStateOf("")
    var avgMinPrice by mutableStateOf("")
    var avgMaxPrice by mutableStateOf("")
    var quantityType by mutableStateOf("")
    var starCount by  mutableStateOf("")
    var subType by mutableStateOf("")
    var itemType by mutableStateOf("")
    var retrievedType by mutableStateOf("")
    var searchKeywords by  mutableStateOf("")
    var imageUrl by mutableStateOf(Uri.EMPTY)
    var quantityTypeDropDownExpanded by mutableStateOf(false)
    var starCountDropDownExpanded by mutableStateOf(false)
    var typeDropDownExpanded by mutableStateOf(false)
    var subTypeDropDownExpanded by mutableStateOf(false)
    var idReadOnly by mutableStateOf(false)
    var showError by mutableStateOf(false)
    var itemFound by mutableStateOf(false)


    fun refactorIdCorrectly(){
        id =
            id.split(" ")
                .filter { it.isNotBlank() }
                .joinToString(" ") {
                    it.trim().replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                }
                .replace(".","")
                .trim()
    }

    fun refactorDescription(){
        description = description
            .split(" ")
            .filter { it.isNotBlank() && it.lowercase() != id.lowercase() }
            .joinToString(" ")
            .trim()
    }

    fun refactorPriceToInt(){
        avgMinPrice = avgMinPrice.toIntOrNull()?.toString() ?: ""
        avgMaxPrice = avgMaxPrice.toIntOrNull()?.toString() ?: ""
    }


    fun refactorKeywords(){
        viewModelScope.launch(Dispatchers.IO){
            searchKeywords = searchKeywords
                .split(",")
                .filter { it.isNotBlank() }
                .joinToString(",") {
                    it.replace(".","").trim().lowercase()
                }
            searchKeywords=searchKeywords
                .split(" ")
                .filter { it.isNotBlank() }
                .joinToString(" ")
        }
    }

    fun checkAllItemBeforeUploading(show:Boolean): Boolean {
        var isValid = true
        showError = show
        when {
            id.isEmpty() -> isValid = false
            description.isEmpty() -> isValid = false
            avgMinPrice.isEmpty() -> isValid = false
            avgMaxPrice.isEmpty() -> isValid = false
            quantityType.isEmpty() -> isValid = false
            starCount.isEmpty() -> isValid = false
            searchKeywords.isEmpty() -> isValid = false
            imageUrl == Uri.EMPTY -> isValid = false
            subType.isEmpty() -> isValid = false
            itemType.isEmpty() -> isValid = false
        }

        return isValid
    }


    fun uploadItem(){
        launchCaching {
            if(retrievedType != "" && retrievedType != itemType){
                currentButtonLoading = CurrentButtonLoading.DELETING
                fireBaseStorage.deleteImage(retrievedType,id.lowercase())
            }
            currentButtonLoading = CurrentButtonLoading.UPLOADING
            val newUrl =fireBaseStorage.uploadImage(imageUrl, itemType.lowercase(), id.lowercase())
            if (newUrl != Uri.EMPTY) {
                fireBaseDatabase.addOrUpdateItem(
                    ItemInsert(
                        id = id,
                        description = description,
                        avgPrice = "₹$avgMinPrice-₹$avgMaxPrice per $quantityType",
                        image = newUrl.toString(),
                        quantityType = quantityType.lowercase(),
                        starCount = starCount,
                        subType = subType.lowercase(),
                        type = itemType.lowercase(),
                        searchKeywords = searchKeywords,
                        userId = fireBaseAccount.currentUser ?: ""
                    )
                ).collect {
                    if (it) {
                        id = ""
                        description = ""
                        avgMinPrice = ""
                        avgMaxPrice = ""
                        quantityType = ""
                        starCount = ""
                        subType = ""
                        itemType = ""
                        searchKeywords = ""
                        imageUrl = Uri.EMPTY
                        currentButtonLoading = CurrentButtonLoading.NONE
                        showError = false
                        idReadOnly = false
                        retrievedType = ""
                    }
                }
            }
        }
    }


    fun getItem(context: Context){
        launchCaching {
            fireBaseDatabase.getItemDetails(id).collect{
                if(it != null){
                    imageUrl = Uri.EMPTY
                    id = it.id
                    description = it.description
                    avgMinPrice = it.avgPrice.split("-")[0].replace("₹","")
                    avgMaxPrice = it.avgPrice.split("-")[1].replace("₹","").substringBefore(" ")
                    quantityType = it.quantityType
                    starCount = it.starCount
                    subType = it.subType
                    itemType = it.type
                    searchKeywords = it.searchKeywords
                    imageUrl = fireBaseStorage.getImageFromOnline(context,it.image,it.id)
                    itemFound = true
                    retrievedType = it.type
                    showError = false
                }else{
                    Toast.makeText(context,"Item Not Found",Toast.LENGTH_SHORT).show()
                    itemFound = false
                }
            }
        }
    }

    fun clearEveryThing(){
        id = ""
        description = ""
        avgMinPrice = ""
        avgMaxPrice = ""
        quantityType = ""
        starCount = ""
        subType = ""
        itemType = ""
        searchKeywords = ""
        imageUrl = Uri.EMPTY
        retrievedType = ""
        showError = false
    }


    fun deleteItem(){
        launchCaching {
            currentButtonLoading = CurrentButtonLoading.DELETING
            if(fireBaseStorage.deleteImage(itemType,id.lowercase())){
                fireBaseDatabase.deleteItem(id)
                clearEveryThing()
                idReadOnly = false
                currentButtonLoading = CurrentButtonLoading.NONE
            }
        }
    }


    fun signOut(navigate:(NavigationRoutes) -> Unit){
        launchCaching {
            fireBaseAccount.signOut()
            navigate(NavigationRoutes.LoginScreen)
        }
    }
}