package com.rspk.adminapp.compose.login

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rspk.adminapp.common.snackbar.SnackBarManager
import com.rspk.adminapp.compose.MainViewModel
import com.rspk.adminapp.constants.CurrentButtonLoading
import com.rspk.adminapp.firebase.FireBaseAccount
import com.rspk.adminapp.firebase.FireBaseDatabase
import com.rspk.adminapp.firebase.FireBaseStorage
import com.rspk.adminapp.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val fireBaseAccount: FireBaseAccount,
    private val fireBaseDatabase: FireBaseDatabase,
    private val fireBaseStorage: FireBaseStorage,
    private val auth: FirebaseAuth
) : MainViewModel() {

    var doesForgotEmailSent by mutableStateOf(false)


    fun login(email: String, password: String, loginNavigation: (NavigationRoutes) -> Unit) {
        launchCaching {
            if (fireBaseAccount.signInWithEmailAccount(email, password)) {
                currentButtonLoading = CurrentButtonLoading.LOGIN
                if (fireBaseDatabase.checkIsHeAdmin()) {
                    loginNavigation(NavigationRoutes.HomeScreen)
                } else {
                    SnackBarManager.showMessage("You Are Not Admin")
                    loginNavigation(NavigationRoutes.NotAdmin)
                }
            }
            currentButtonLoading = CurrentButtonLoading.NONE
        }
    }

    fun forgotPassword(email: String) {
        launchCaching {
            currentButtonLoading = CurrentButtonLoading.FORGOT_PASSWORD
            doesForgotEmailSent = fireBaseAccount.forgotPassword(email)
            currentButtonLoading = CurrentButtonLoading.NONE
            delay(2000L)
            doesForgotEmailSent = false
        }
    }

}