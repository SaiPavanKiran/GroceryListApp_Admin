package com.rspk.adminapp.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.adminapp.R
import com.rspk.adminapp.common.modifiers.welcomeEmailButtonColors
import com.rspk.adminapp.common.modifiers.welcomeScreenButtonModifier
import com.rspk.adminapp.common.modifiers.welcomeTextButtonColors

@Composable
fun ForgotPassword(
    onClick:() -> Unit
){
    TextButton(onClick = {
        onClick()
    },
        colors = ButtonDefaults.welcomeTextButtonColors(),
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .wrapContentWidth(Alignment.End)
            .padding(bottom = dimensionResource(id = R.dimen.padding_10))
    ) {
        Text(stringResource(id = R.string.forgot_password))
    }
}


@Composable
fun SigningButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    conditionForLoading: Boolean,
    onClick: () -> Unit,
){
    Button(
        onClick = {
            onClick()
        }, enabled = enabled,
        modifier = modifier
            .welcomeScreenButtonModifier(width = 0.75f),
        colors = ButtonDefaults.welcomeEmailButtonColors()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_7)),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.continue_text))
            if (conditionForLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_20)),
                    color = colorResource(id = R.color.circular_progress_color)
                )
            }
        }
    }
}


@Composable
fun CustomUploadButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text:String,
    colors: ButtonColors? = null,
    enabled: Boolean = true
){
    Button(onClick = {
        onClick()
    },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        colors = colors ?: ButtonDefaults.welcomeEmailButtonColors(),
        enabled = enabled
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = text
        )
    }
}


@Composable
fun CustomTextButton(
    text: String,
    icon: ImageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
    onIconButtonClick:() -> Unit,
    onClick: () -> Unit
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        TextButton(onClick = {
            onClick()
        },
            modifier = Modifier
                .padding(vertical = 5.dp),
            colors = ButtonDefaults.textButtonColors(
                contentColor = colorResource(id = R.color.text_button_text_color)
            )) {
            Text(text = text)
        }
        IconButton(onClick = {
            onIconButtonClick()
        }) {
            Icon(
                imageVector = icon,
                contentDescription = "Sign Out"
            )
        }
    }
}