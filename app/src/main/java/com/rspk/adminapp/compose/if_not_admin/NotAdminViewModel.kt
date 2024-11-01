package com.rspk.adminapp.compose.if_not_admin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rspk.adminapp.compose.MainViewModel
import com.rspk.adminapp.firebase.FireBaseAccount
import com.rspk.adminapp.firebase.FireBaseDatabase
import com.rspk.adminapp.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotAdminViewModel @Inject constructor(
    private val fireBaseDatabase: FireBaseDatabase,
    private val fireBaseAccount: FireBaseAccount
):MainViewModel() {

    var buttonEnable by mutableStateOf(false)

    init {
        viewModelScope.launch {
            buttonEnable = !(fireBaseDatabase.isAlreadyRequested())
            Log.d("TesTingProgress","Button Enable: $buttonEnable")
            Log.d("TesTingProgress","Already Requested: ${fireBaseDatabase.isAlreadyRequested()}")
        }
    }

    fun onRequestButtonClicked(navigate:(NavigationRoutes)->Unit){
        launchCaching {
            if(fireBaseDatabase.requestAdmin()) {
                navigate(NavigationRoutes.LoginScreen)
            }
        }
    }

    fun signOut(navigate:(NavigationRoutes)->Unit){
        launchCaching {
            fireBaseAccount.signOut()
            navigate(NavigationRoutes.LoginScreen)
        }
    }

}