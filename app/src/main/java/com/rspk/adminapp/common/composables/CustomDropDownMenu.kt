package com.rspk.adminapp.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.adminapp.R
import com.rspk.adminapp.constants.CurrentScreen
import com.rspk.adminapp.model.DropDownInfo
import com.rspk.adminapp.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    textFieldComposable: @Composable (Modifier) -> Unit = {},
    onTextFieldValueChange: (String) -> Unit,
    items:List<String> = emptyList()
) {
    var currentValue by rememberSaveable { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpandedChanged,
        modifier = modifier
    ) {
        AnchoredTextField(
            textFieldComposable = textFieldComposable
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChanged(false) },
            modifier = Modifier
                .background(colorResource(id = R.color.dropdown_menu_background_color))
                .wrapContentSize(unbounded = true)
                .fillMaxWidth(),
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier= Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = it,
                                fontSize = 10.sp,
                                color = colorResource(id = R.color.dropdown_menu_text_color)
                            )
                            if(it == currentValue){
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.dropdown_menu_text_color)
                                )
                            }
                        }
                    },
                    onClick = {
                        currentValue =it
                        onTextFieldValueChange(it)
                        onExpandedChanged(false)
                    },
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.padding_20))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.AnchoredTextField(
    textFieldComposable:@Composable (Modifier) -> Unit,
){
    textFieldComposable(
        Modifier
            .menuAnchor(
                type = MenuAnchorType.PrimaryEditable,
                enabled = true
            )
    )
}


@Composable
fun TwoDropDownInRow(
    firstItem: DropDownInfo,
    secondItem: DropDownInfo
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        CustomDropDownMenu(
            isExpanded = firstItem.isExpanded,
            onExpandedChanged = firstItem.onExpandedChanged,
            textFieldComposable = {
                CustomDropDownTextField(
                    value = firstItem.textFieldValue ,
                    modifier = it,
                    label = firstItem.label,
                    isError = firstItem.isError
                )
            },
            onTextFieldValueChange = firstItem.onTextFieldValueChange,
            items = firstItem.items,
            modifier = Modifier
                .weight(0.97f)
        )
        Spacer(modifier = Modifier.weight(0.06f))
        CustomDropDownMenu(
            isExpanded = secondItem.isExpanded ,
            onExpandedChanged = secondItem.onExpandedChanged,
            textFieldComposable = {
                CustomDropDownTextField(
                    value = secondItem.textFieldValue,
                    modifier = it,
                    label = secondItem.label,
                    isError = secondItem.isError
                )
            },
            onTextFieldValueChange = secondItem.onTextFieldValueChange,
            items = secondItem.items,
            modifier = Modifier
                .weight(0.97f)
        )
    }
}

