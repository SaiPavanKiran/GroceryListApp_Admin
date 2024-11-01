package com.rspk.adminapp.common.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.rspk.adminapp.R

@Composable
fun TextFieldDefaults.customTextFieldColors():TextFieldColors{
    return this.colors(
        unfocusedContainerColor = colorResource(id = R.color.text_field_background_color),
        focusedContainerColor = colorResource(id = R.color.text_field_background_color),
        unfocusedTextColor = colorResource(id = R.color.text_field_text_color),
        focusedTextColor = colorResource(id = R.color.text_field_text_color),
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedPlaceholderColor = colorResource(id = R.color.text_field_placeholder_color),
        focusedPlaceholderColor = colorResource(id = R.color.text_field_placeholder_color),
        cursorColor = colorResource(id = R.color.text_field_text_color)
    )
}

@Composable
fun OutlinedTextFieldDefaults.uploadDataTextFieldColors(): TextFieldColors {
    return this.colors(
        focusedTextColor = colorResource(id = R.color.new_text_field_content),
        unfocusedTextColor = colorResource(id = R.color.new_text_field_content),
        errorTextColor = colorResource(id = R.color.new_text_field_error_text),
        focusedContainerColor = colorResource(id = R.color.new_text_field_container),
        unfocusedContainerColor = colorResource(id = R.color.new_text_field_container),
        errorContainerColor = colorResource(id = R.color.new_text_field_container),
        focusedBorderColor = colorResource(id = R.color.new_text_field_border_focused),
        unfocusedBorderColor = colorResource(id = R.color.new_text_field_border_unfocused),
        errorBorderColor = colorResource(id = R.color.new_text_field_error_border),
        cursorColor = colorResource(id = R.color.new_text_field_cursor),
        errorCursorColor = colorResource(id = R.color.new_text_field_error_text),
        focusedPlaceholderColor = colorResource(id = R.color.new_text_field_placeholder),
        unfocusedPlaceholderColor = colorResource(id = R.color.new_text_field_placeholder),
        errorPlaceholderColor = colorResource(id = R.color.new_text_field_error_text),
        focusedLabelColor = colorResource(id = R.color.new_text_field_label_focused),
        unfocusedLabelColor = colorResource(id = R.color.new_text_field_label_unfocused),
        errorLabelColor = colorResource(id = R.color.new_text_field_error_text),
        focusedLeadingIconColor = colorResource(id = R.color.new_text_field_leading_icon),
        unfocusedLeadingIconColor = colorResource(id = R.color.new_text_field_leading_icon),
        errorLeadingIconColor = colorResource(id = R.color.new_text_field_error_icon),
        focusedTrailingIconColor = colorResource(id = R.color.new_text_field_trailing_icon),
        unfocusedTrailingIconColor = colorResource(id = R.color.new_text_field_trailing_icon),
        errorTrailingIconColor = colorResource(id = R.color.new_text_field_error_icon)
    )
}




@Composable
fun Modifier.customTextFieldModifier():Modifier {
    return this
        .height(dimensionResource(id = R.dimen.size_60))
        .fillMaxWidth(0.75f)
        .shadow(
            elevation = 10.dp,
            ambientColor = Color.Black.copy(0.2f),
            shape = RoundedCornerShape(30.dp),
            clip = true
        )
}

@Composable
fun Modifier.basicTextFieldModifier():Modifier{
    return this
        .fillMaxWidth(0.95f)
        .height(dimensionResource(id = R.dimen.size_47))

}

@Composable
fun Modifier.basicTextFieldContainerModifier():Modifier{
    return this
        .fillMaxSize()
        .height(dimensionResource(id = R.dimen.size_47))
        .clip(RoundedCornerShape(7.dp))
        .background(colorResource(id = R.color.top_text_field_container))
        .border(
            width = 0.3.dp,
            color = colorResource(id = R.color.cart_buttons_color),
            shape = RoundedCornerShape(7.dp)
        )
        .padding(horizontal = dimensionResource(id = R.dimen.padding_10))
}