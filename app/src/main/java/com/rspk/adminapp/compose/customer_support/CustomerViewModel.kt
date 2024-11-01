package com.rspk.adminapp.compose.customer_support

import androidx.lifecycle.ViewModel
import com.rspk.adminapp.compose.MainViewModel
import com.rspk.adminapp.firebase.FireBaseAccount
import com.rspk.adminapp.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val fireBaseAccount: FireBaseAccount
):MainViewModel() {

    fun signOut(navigate:(NavigationRoutes) -> Unit){
        launchCaching {
            fireBaseAccount.signOut()
            navigate(NavigationRoutes.LoginScreen)
        }
    }
}