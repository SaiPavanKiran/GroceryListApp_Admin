package com.rspk.adminapp.navigation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rspk.adminapp.firebase.FireBaseAccount
import com.rspk.adminapp.firebase.FireBaseDatabase
import com.rspk.adminapp.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val fireBaseAccount: FireBaseAccount,
    private val fireBaseDatabase: FireBaseDatabase
):ViewModel() {

    var startDestination by mutableStateOf<NavigationRoutes?>(null)

    init {
        currentRoute()
    }

    private fun currentRoute() {
        viewModelScope.launch {
            if (fireBaseAccount.hasUser) {
                if (fireBaseDatabase.checkIsHeAdmin()) {
                    startDestination = NavigationRoutes.HomeScreen
                } else {
                    startDestination = NavigationRoutes.NotAdmin
                }
            } else {
                startDestination = NavigationRoutes.WelcomeScreen
            }
        }
    }
}