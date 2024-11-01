package com.rspk.adminapp.compose.customer_support

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rspk.adminapp.common.composables.CustomTextButton
import com.rspk.adminapp.navigation.NavigationRoutes

@Composable
fun CustomerSupport(
    navigate: (NavigationRoutes) -> Unit,
    clearAndNavigate:(NavigationRoutes) -> Unit,
    customerViewModel: CustomerViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextButton(
            text = "Want To Add New Item??",
            onIconButtonClick = {
                customerViewModel.signOut(clearAndNavigate)
            },
            onClick = {
                navigate(NavigationRoutes.HomeScreen)
            }
        )
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = "We Regret this Screen is Not Available Currently\uD83D\uDE13."+
            "And We Except It To Be Delayed\uD83D\uDE2B."+
            "We Deeply Regret For Any Inconvenience Caused\uD83D\uDE14." +
            "Thank You For Your Patience",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            style = TextStyle(
                textAlign = TextAlign.Center
            )
        )
    }
}