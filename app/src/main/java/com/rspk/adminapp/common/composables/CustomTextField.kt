package com.rspk.adminapp.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.rspk.adminapp.R
import com.rspk.adminapp.common.modifiers.customTextFieldColors
import com.rspk.adminapp.common.modifiers.customTextFieldModifier
import com.rspk.adminapp.common.modifiers.textStyleForSignUpScreen
import com.rspk.adminapp.common.modifiers.uploadDataTextFieldColors


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    placeholder: String? = null,
    trailingIcon: Painter? = null,
    leadingIcon: Painter? = null,
    trailingIconClick: () -> Unit = {},
    keyboardOptions: KeyboardOptions? = null,
    keyboardActions: KeyboardActions? = null,
    visualTransformation: VisualTransformation? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.customTextFieldModifier(),
        singleLine = singleLine,
        textStyle = textStyleForSignUpScreen(),
        shape = RoundedCornerShape(50.dp),
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    modifier = Modifier,
                    style = textStyleForSignUpScreen()
                )
            }
        },
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = "Icon",
                )
            }
        },
        trailingIcon = {
            trailingIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .clickable {
                            trailingIconClick()
                        },
                    tint = colorResource(id = R.color.text_field_text_color)
                )
            }
        },
        maxLines = 1,
        keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
        keyboardActions = keyboardActions ?: KeyboardActions.Default,
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        colors = TextFieldDefaults.customTextFieldColors()
    )
}

@Composable
fun CustomDropDownTextField(
    modifier: Modifier = Modifier,
    value: String,
) {
    BasicTextField(
        value = value,
        onValueChange = {},
        modifier = modifier,
        interactionSource = null,
        readOnly = true,
        textStyle = TextStyle(
            color = colorResource(id = R.color.grocery_text_color),
            textAlign = TextAlign.Center
        )
    )
}

@Composable
fun CustomDropDownTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    leadingIcon: ImageVector? = null,
    isError: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = modifier,
        leadingIcon = leadingIcon?.let { {
                Icon(
                    imageVector = it,
                    contentDescription = "Open"
                )
            }
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open"
            )
        },
        shape = RoundedCornerShape(9.dp),
        label = {
            Text(
                text = label,
            )
        },
        singleLine = true,
        interactionSource = null,
        readOnly = true,
        isError = isError,
        colors = OutlinedTextFieldDefaults.uploadDataTextFieldColors()
    )
}

@Composable
fun CustomTextFieldWithHeading(
    modifier: Modifier = Modifier,
    text: String,
    userNoticeString: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions? = null,
    onFocusChanged: () -> Unit = {},
    isError: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconClick: () -> Unit = {},
    singleLine: Boolean = true,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
        shape = RoundedCornerShape(9.dp),
        colors = OutlinedTextFieldDefaults.uploadDataTextFieldColors(),
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        },
        trailingIcon =trailingIcon?.let { {

                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            trailingIconClick()
                        }
                )
            }
        },
        isError = isError,
        label = {
            Text(text = text)
        },
        singleLine = singleLine ,
        supportingText = userNoticeString?.let {
            {
                if (isError)
                    CustomSupportingText(text = userNoticeString)
            }
        },
        modifier = modifier
            .fillMaxSize()
            .onFocusChanged {
                if (!it.isFocused) {
                    onFocusChanged()
                }
            },
    )
}