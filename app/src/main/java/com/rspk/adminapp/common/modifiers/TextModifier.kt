package com.rspk.adminapp.common.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun textStyleForSignUpScreen():TextStyle {
    return TextStyle(
        fontWeight = FontWeight.Bold,
    )
}