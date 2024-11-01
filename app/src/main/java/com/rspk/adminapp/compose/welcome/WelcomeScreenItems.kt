package com.rspk.adminapp.compose.welcome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rspk.adminapp.R
import com.rspk.adminapp.common.modifiers.welcomeEmailButtonColors
import com.rspk.adminapp.common.modifiers.welcomeScreenButtonModifier
import com.rspk.adminapp.constants.CurrentButtonLoading
import com.rspk.adminapp.navigation.NavigationRoutes

@Composable
fun GreetingsWIthImage(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int = R.drawable._140741_3528493,
    greetings: String = "Welcome to ByIt_Admin!",
    tagLine: String = "This App Is Only For Admins"
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_7))
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "App Image" ,
            modifier = Modifier.size(dimensionResource(id = R.dimen.welcome_image_size))
        )
        Text(
            text = greetings,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.welcome_text_color)
        )
        Text(
            text = tagLine,
            fontSize = 12.sp,
            color = colorResource(id = R.color.tagline_text_color)
        )
    }
}

@Composable
fun ConnectButtons(
    modifier: Modifier = Modifier,
    navigate: (NavigationRoutes) -> Unit,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
){
    val enabled = welcomeViewModel.currentButtonLoading == CurrentButtonLoading.NONE
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Connect With",
            fontSize = 14.sp,
            color = colorResource(id = R.color.tagline_text_color),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_7)),
        )
        Button(
            onClick = {
                navigate(NavigationRoutes.LoginScreen)
            },
            enabled = enabled,
            modifier = Modifier.welcomeScreenButtonModifier(),
            colors = ButtonDefaults.welcomeEmailButtonColors()
        ) {
            Text(
                text = "Email",
                color = colorResource(id = R.color.email_button_text_color),
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_5))
            )
        }

    }
}
