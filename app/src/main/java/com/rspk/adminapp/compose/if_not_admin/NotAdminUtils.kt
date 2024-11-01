package com.rspk.adminapp.compose.if_not_admin

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.adminapp.R
import com.rspk.adminapp.common.snackbar.SnackBarManager
import com.rspk.adminapp.navigation.NavigationRoutes

@Composable
fun getLetterHead() =
    buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.letter_body_text_color),
                fontSize = 14.sp
            )
        ) {
            append(stringResource(id = R.string.dear_user))
        }
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.letter_heading_text_color)
                , fontSize = 24.sp, fontWeight = FontWeight.SemiBold
            )
        ) {
            append(stringResource(id = R.string.want_to_be_admin))
        }

        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.letter_body_text_color),
                fontSize = 17.sp
            )
        ) {
            append(stringResource(id = R.string.use_real_email))
        }
    }

@Composable
fun getTextBasedOnButtonState(buttonEnabled:Boolean) =
    buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.letter_body_text_color),
                fontSize = 17.sp
            )
        ) {
            if(buttonEnabled) {
                append(stringResource(id = R.string.request_button_text))
            }else{
                append(stringResource(id = R.string.already_request_button_text))
            }
        }
    }


@Composable
fun requestButton() =
    buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = colorResource(id = R.color.letter_text_button_text_color)
            )
        ) {
            append(stringResource(id = R.string.request_button))
        }
    }



@Composable
fun letterBottomText() =
    buildAnnotatedString {
        withStyle(style = SpanStyle(color = colorResource(id = R.color.letter_body_text_color), fontSize = 17.sp)){
            append(stringResource(id = R.string.once_you_submit))
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(id = R.string.days))
            }
            append(stringResource(id = R.string.appolize_not_accepting))
        }

        withStyle(style = SpanStyle(color = colorResource(id = R.color.letter_body_text_color), fontSize = 17.sp)) {
            append(stringResource(id = R.string.after_submitting_request))
        }

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.letter_warning_text_color), fontSize = 17.sp)) {
            append(stringResource(id = R.string.dont_misuse_admin_access))
        }
    }


@Composable
fun CustomVerticalDivider(){
    VerticalDivider(
        thickness = 3.dp,
        modifier = Modifier
            .padding(start = 65.dp)
            .fillMaxHeight(0.78f),
        color = colorResource(id = R.color.letter_vertical_divider_color)
    )
}


@Composable
fun CustomHorizontalDivider(
    offset: Dp
){
    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier
            .offset(x=offset)
    )
}

@Composable
fun LeftAlignedTextWithSignOutButton(
    notAdminViewModel: NotAdminViewModel,
    configuration: Configuration = LocalConfiguration.current,
    clearAndNavigate:(NavigationRoutes) -> Unit,
    buttonEnabled:Boolean
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .statusBarsPadding()
            .padding(top = if (buttonEnabled) 30.dp else 90.dp)
    ){
        IconButton(onClick = {
            notAdminViewModel.signOut(
                navigate = clearAndNavigate
            )
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                contentDescription = "Sign Out",
                tint = colorResource(id = R.color.letter_body_text_color)
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(unbounded = true, align = Alignment.Center)
            .rotate(90f)
            .offset(y = configuration.screenWidthDp.dp / 2 - 23.dp)
    ) {
        Text(
            text = stringResource(id = R.string.main_rotated_text),
            fontFamily = FontFamily(Font(R.font.pacifico_regular)),
            fontSize = 55.sp,
            color = colorResource(id = R.color.letter_rotated_text_color),
        )
    }
}



@Composable
fun Agreement(
    @DrawableRes acceptIcon:Int,
    onAcceptedIconChange:(Int) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "I Accept This Letter",
            fontFamily = FontFamily(Font(R.font.greatvibes_regular)) ,
            fontSize = 30.sp,
            color = colorResource(id = R.color.letter_agreed_text_color)
        )
        IconButton(onClick = {
            if(acceptIcon == R.drawable.thumb_up_svgrepo_com){
                onAcceptedIconChange(R.drawable.thumb_up_svgrepo_com__2_)
            }else{
                onAcceptedIconChange(R.drawable.thumb_up_svgrepo_com)
            }
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = acceptIcon),
                contentDescription = "Agreed",
                tint = colorResource(id = R.color.letter_agreed_text_color)
            )
        }
    }
}


@Composable
fun CustomTextButton(
    notAdminViewModel: NotAdminViewModel,
    clearAndNavigate: (NavigationRoutes) -> Unit,
    @DrawableRes acceptIcon: Int,
){
    TextButton(
        onClick = {
            if(acceptIcon != R.drawable.thumb_up_svgrepo_com__2_){
                SnackBarManager.showMessage("First Click on 👍(Thumb Icon)")
            }else{
                notAdminViewModel.buttonEnable = false
                notAdminViewModel.onRequestButtonClicked(
                    navigate = clearAndNavigate
                )
            }
        },
        enabled = notAdminViewModel.buttonEnable,
        modifier = Modifier
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp)
    ) {
        Text(
            text = requestButton(),
            fontFamily = FontFamily(Font(R.font.deliusswashcaps_regular))
        )
    }
}

@Composable
fun CustomLetterText(
    modifier: Modifier = Modifier,
    text:AnnotatedString
){
    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.deliusswashcaps_regular)),
        modifier = modifier
            .padding(horizontal = 20.dp)
    )
}