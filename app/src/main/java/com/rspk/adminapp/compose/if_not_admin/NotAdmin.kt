package com.rspk.adminapp.compose.if_not_admin

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rspk.adminapp.R
import com.rspk.adminapp.common.snackbar.SnackBarManager
import com.rspk.adminapp.navigation.NavigationRoutes

@Composable
fun NotAdmin(
    notAdminViewModel: NotAdminViewModel = hiltViewModel(),
    configuration: Configuration = LocalConfiguration.current,
    clearAndNavigate:(NavigationRoutes) -> Unit
) {
    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        LetterPortrait(
            clearAndNavigate = clearAndNavigate,
            notAdminViewModel = notAdminViewModel
        )
    }else if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
        LetterLandscape()
    }
}

@Composable
fun LetterPortrait(
    notAdminViewModel: NotAdminViewModel = hiltViewModel(),
    clearAndNavigate:(NavigationRoutes) -> Unit
){
    var acceptIcon by rememberSaveable { mutableIntStateOf(R.drawable.thumb_up_svgrepo_com) }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .statusBarsPadding()
            .background(colorResource(id = R.color.letter_background_color)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomVerticalDivider()
        Column {
            CustomHorizontalDivider(offset = (-20).dp)
            CustomLetterText(text = getLetterHead(), modifier = Modifier.padding(top = 20.dp) )
            CustomLetterText(text = getTextBasedOnButtonState(buttonEnabled = notAdminViewModel.buttonEnable))
            if(notAdminViewModel.buttonEnable){
                CustomTextButton(
                    notAdminViewModel = notAdminViewModel,
                    clearAndNavigate = clearAndNavigate ,
                    acceptIcon = acceptIcon
                )
            }
            CustomLetterText(text = letterBottomText())
            if(notAdminViewModel.buttonEnable)
                Agreement(acceptIcon = acceptIcon, onAcceptedIconChange = { acceptIcon = it })
            else Spacer(modifier = Modifier.height(20.dp))
            CustomHorizontalDivider(offset = 20.dp)
        }
    }
    LeftAlignedTextWithSignOutButton(
        notAdminViewModel = notAdminViewModel ,
        clearAndNavigate = clearAndNavigate,
        buttonEnabled = notAdminViewModel.buttonEnable
    )
}


@Composable
fun LetterLandscape(){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.letter))
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(200.dp),
            iterations = LottieConstants.IterateForever
        )
        Text(
            text = "Enable Portrait Mode\nTo open Letter",
            fontSize = 17.sp,
            color = colorResource(id = R.color.letter_body_text_color),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.deliusswashcaps_regular))
        )
    }
}