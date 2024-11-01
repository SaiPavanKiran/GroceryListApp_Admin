package com.rspk.adminapp.compose.welcome

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.rspk.adminapp.R
import com.rspk.adminapp.navigation.NavigationRoutes

@Composable
fun WelcomeScreen(
    navigate: (NavigationRoutes) -> Unit,
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
    configuration: Configuration = LocalConfiguration.current
) {
    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        WelcomeScreenPortraitContent(
            welcomeViewModel = welcomeViewModel,
            navigate = navigate,
        )
    }else if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
        WelcomeScreenLandscapeContent(
            welcomeViewModel = welcomeViewModel,
            navigate = navigate,
        )
    }
}

@Composable
fun WelcomeScreenPortraitContent(
    welcomeViewModel: WelcomeViewModel,
    navigate: (NavigationRoutes) -> Unit,
){
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = dimensionResource(id = R.dimen.padding_20)),
    ) {
        GreetingsWIthImage()
        Spacer(modifier = Modifier.weight(0.5f))
        ConnectButtons(
            navigate = navigate,
            welcomeViewModel = welcomeViewModel
        )
        Spacer(modifier = Modifier.weight(0.4f))
    }
}

@Composable
fun WelcomeScreenLandscapeContent(
    welcomeViewModel: WelcomeViewModel,
    navigate: (NavigationRoutes) -> Unit,
){
    Row{
       GreetingsWIthImage(
           modifier = Modifier.weight(1f)
       )
        ConnectButtons(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            navigate = navigate,
            welcomeViewModel = welcomeViewModel
        )
    }
}