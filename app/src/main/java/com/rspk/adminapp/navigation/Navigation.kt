package com.rspk.adminapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rspk.adminapp.R
import com.rspk.adminapp.compose.home.AddScreen
import com.rspk.adminapp.compose.customer_support.CustomerSupport
import com.rspk.adminapp.compose.if_not_admin.NotAdmin
import com.rspk.adminapp.compose.login.LoginScreen
import com.rspk.adminapp.compose.welcome.WelcomeScreen
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    navigationViewModel: NavigationViewModel = hiltViewModel(),
) {
    val navigationState = rememberNavigationState()
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = navigationState.snackBarHostState,
                modifier = Modifier.padding(8.dp),
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        containerColor = colorResource(id = R.color.snack_bar_container_color),
                        contentColor = colorResource(id = R.color.snack_bar_content_color),
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        navigationViewModel.startDestination?.let {
            NavHost(
                navController = navigationState.navController,
                startDestination = it,
            ) {
                this.appNavigationGraph(
                    navigationState = navigationState
                )
            }
        }
    }
}



@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) =
    remember(navController,snackBarHostState,coroutineScope){
        NavigationState(navController = navController, snackBarHostState = snackBarHostState, coroutineScope = coroutineScope )
    }



fun NavGraphBuilder.appNavigationGraph(
    navigationState: NavigationState,
){
    composable<NavigationRoutes.WelcomeScreen> {
        WelcomeScreen(
            navigate = {
                navigationState.navigate(it)
            },
        )
    }

    composable<NavigationRoutes.LoginScreen> {
        LoginScreen(
            loginNavigation = {
                navigationState.clearAndNavigate(it)
            }
        )
    }

    composable<NavigationRoutes.HomeScreen> {
        AddScreen(
            navigate = {
                navigationState.navigate(it)
            },
            clearAndNavigate = {
                navigationState.clearAndNavigate(it)
            }
        )
    }

    composable<NavigationRoutes.CustomerService> {
        CustomerSupport(
            navigate = {
                navigationState.navigate(it)
            },
            clearAndNavigate = {
                navigationState.clearAndNavigate(it)
            }
        )
    }

    composable<NavigationRoutes.NotAdmin> {
        NotAdmin(
            clearAndNavigate = {
                navigationState.clearAndNavigate(it)
            }
        )
    }
}