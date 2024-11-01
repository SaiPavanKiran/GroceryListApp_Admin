package com.rspk.adminapp.compose.welcome

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.rspk.adminapp.compose.MainViewModel
import com.rspk.adminapp.firebase.FireBaseAccount
import com.rspk.adminapp.firebase.FireBaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val fireBaseAccount: FireBaseAccount,
    private val fireBaseDataBase: FireBaseDatabase,
    private val auth:FirebaseAuth
): MainViewModel() {


    companion object{
        private const val ERROR_TAG = "WelcomeViewModel"
        private const val UNEXPECTED_CREDENTIAL = "Unexpected credential"
    }
}