package com.rspk.adminapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoutes(){

    @Serializable
    data object WelcomeScreen: NavigationRoutes()

    @Serializable
    data object LoginScreen : NavigationRoutes()

    @Serializable
    data object CustomerService:NavigationRoutes()

    @Serializable
    data object HomeScreen: NavigationRoutes()

    @Serializable
    data object NotAdmin: NavigationRoutes()

}