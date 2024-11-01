package com.rspk.adminapp.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class DropDownInfo(
    val isExpanded: Boolean,
    val onExpandedChanged: (Boolean) -> Unit,
    val textFieldValue: String,
    val onTextFieldValueChange:(String) -> Unit,
    val label:String ,
    val items: List<String>,
    val isError:Boolean
)