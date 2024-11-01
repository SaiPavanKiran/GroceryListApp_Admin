package com.rspk.adminapp.compose.home

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rspk.adminapp.R
import com.rspk.adminapp.common.composables.CustomDropDownMenu
import com.rspk.adminapp.common.composables.CustomDropDownTextField
import com.rspk.adminapp.common.composables.CustomTextFieldWithHeading
import com.rspk.adminapp.common.composables.CustomUploadButton
import com.rspk.adminapp.common.composables.TwoDropDownInRow
import com.rspk.adminapp.common.snackbar.SnackBarManager
import com.rspk.adminapp.constants.mappedTypeToSubType
import com.rspk.adminapp.constants.quantitiesList
import com.rspk.adminapp.constants.starCount
import com.rspk.adminapp.constants.type_Item_List
import com.rspk.adminapp.model.DropDownInfo

@Composable
fun Id(
    addViewModel: AddViewModel,
    context: Context = LocalContext.current
){
    CustomTextFieldWithHeading(
        text = "key",
        value = addViewModel.id,
        userNoticeString = "Id Can't Be Empty",
        isError = addViewModel.id.isEmpty() && addViewModel.showError,
        onValueChange = {
            addViewModel.id = it
        }, readOnly = addViewModel.idReadOnly,
        onFocusChanged = {
            addViewModel.refactorIdCorrectly()
        },
        leadingIcon = ImageVector.vectorResource(id = R.drawable.baseline_domain_24),
        trailingIcon =
        if (addViewModel.id != "" && !addViewModel.idReadOnly) {
            ImageVector.vectorResource(id = R.drawable.baseline_cloud_download_24)
        } else if (addViewModel.id != "" && addViewModel.idReadOnly) {
            Icons.Default.Clear
        } else null,
        trailingIconClick = {
            if (addViewModel.id != "" && !addViewModel.idReadOnly) {
                addViewModel.idReadOnly = true
                addViewModel.refactorIdCorrectly()
                addViewModel.getItem(context)
            } else if (addViewModel.id != "" && addViewModel.idReadOnly) {
                addViewModel.idReadOnly = false
                addViewModel.clearEveryThing()
            }
        }
    )
}


@Composable
fun Description(
    addViewModel: AddViewModel
){
    CustomTextFieldWithHeading(
        text = "Description",
        value = addViewModel.description,
        userNoticeString = "Not More Than One Line Is Accepted\ni.e range should be in 30 to 60 ",
        onValueChange = {
            addViewModel.description = it
        },
        isError = (addViewModel.description.isEmpty() || addViewModel.description.length !in 30..60) && addViewModel.showError,
        onFocusChanged = {
            addViewModel.refactorDescription()
        },
        leadingIcon = ImageVector.vectorResource(id = R.drawable.baseline_note_alt_24),
        singleLine = false
    )
}

@Composable
fun TypeAndSubType(
    addViewModel: AddViewModel
){
    TwoDropDownInRow(
        firstItem = DropDownInfo(
            isExpanded = addViewModel.typeDropDownExpanded,
            onExpandedChanged = { addViewModel.typeDropDownExpanded = it },
            items = type_Item_List,
            textFieldValue = addViewModel.itemType,
            onTextFieldValueChange =  {
                addViewModel.itemType = it
                addViewModel.subType = ""
            },
            label = "Type",
            isError = addViewModel.itemType.isEmpty() && addViewModel.showError
        ),
        secondItem = DropDownInfo(
            isExpanded = addViewModel.subTypeDropDownExpanded,
            onExpandedChanged = { addViewModel.subTypeDropDownExpanded = it },
            items = mappedTypeToSubType[addViewModel.itemType] ?: emptyList(),
            textFieldValue = addViewModel.subType,
            onTextFieldValueChange = {
                addViewModel.subType = it
            },
            label = "Sub Type",
            isError = addViewModel.subType.isEmpty() && addViewModel.showError
        )
    )
}

@Composable
fun ImagePreview(
    addViewModel: AddViewModel
){
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "image/*"
    }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.data?.let { uri ->
                    addViewModel.imageUrl = uri
                }
            }
        }
    Box(
        modifier = Modifier
            .padding(vertical = 7.dp)
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (addViewModel.imageUrl == Uri.EMPTY && addViewModel.showError) {
                    colorResource(id = R.color.new_text_field_error_border)
                } else colorResource(id = R.color.new_text_field_border_unfocused),
                shape = RoundedCornerShape(9.dp)
            )
            .background(colorResource(id = R.color.new_text_field_container))
            .clickable {
                launcher.launch(intent)
            },
        contentAlignment = Alignment.Center
    ){
        if (addViewModel.imageUrl == Uri.EMPTY){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_cloud_upload_24),
                    contentDescription = "Image To Upload",
                    modifier = Modifier
                        .size(50.dp),
                    tint = colorResource(id = R.color.new_text_field_leading_icon)
                )
                Text(
                    text = "Upload Image",
                    color = colorResource(id = R.color.new_text_field_content),
                )
            }
        }else{
            Image(
                painter = rememberAsyncImagePainter(addViewModel.imageUrl),
                contentDescription = "Preview Image",
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun MinAndMaxPrice(
    addViewModel: AddViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        CustomTextFieldWithHeading(
            text = "Min",
            value = addViewModel.avgMinPrice,
            onValueChange = {
                addViewModel.avgMinPrice = it
            },
            modifier = Modifier
                .weight(0.97f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            leadingIcon = ImageVector.vectorResource(id = R.drawable.baseline_currency_rupee_24),
            isError = (addViewModel.avgMinPrice.isEmpty()||addViewModel.avgMinPrice.toIntOrNull() == null)
                    && addViewModel.showError,
            onFocusChanged = {
                addViewModel.refactorPriceToInt()
            }
        )
        Spacer(modifier = Modifier.weight(0.06f))
        CustomTextFieldWithHeading(
            modifier = Modifier
                .weight(0.97f),
            text = "Max",
            value = addViewModel.avgMaxPrice,
            onValueChange = {
                addViewModel.avgMaxPrice = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            leadingIcon = ImageVector.vectorResource(id = R.drawable.baseline_currency_rupee_24),
            isError = (addViewModel.avgMaxPrice.isEmpty()||addViewModel.avgMaxPrice.toIntOrNull() == null)
                    && addViewModel.showError,
            onFocusChanged = {
                addViewModel.refactorPriceToInt()
            }
        )
    }
}


@Composable
fun QuantityInAndStarCount(
    addViewModel: AddViewModel
){
    TwoDropDownInRow(
        firstItem = DropDownInfo(
            isExpanded = addViewModel.quantityTypeDropDownExpanded,
            onExpandedChanged = { addViewModel.quantityTypeDropDownExpanded = it },
            items = quantitiesList,
            textFieldValue = addViewModel.quantityType,
            onTextFieldValueChange = {
                addViewModel.quantityType = it
            },
            label = "Quantity In",
            isError = addViewModel.quantityType.isEmpty() && addViewModel.showError
        ),
        secondItem = DropDownInfo(
            isExpanded = addViewModel.starCountDropDownExpanded,
            onExpandedChanged = { addViewModel.starCountDropDownExpanded = it },
            items = starCount,
            textFieldValue = addViewModel.starCount,
            onTextFieldValueChange = {
                addViewModel.starCount = it
            },
            label = "Star Count",
            isError = addViewModel.starCount.isEmpty() && addViewModel.showError
        )
    )
}


@Composable
fun SearchKeywords(
    addViewModel: AddViewModel
){
    CustomTextFieldWithHeading(
        text = "Search Keywords",
        value = addViewModel.searchKeywords,
        onValueChange = {
            addViewModel.searchKeywords = it
        },
        leadingIcon = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_24),
        isError = addViewModel.searchKeywords.isEmpty() && addViewModel.showError,
        trailingIcon = if(addViewModel.searchKeywords.isNotEmpty()){
            ImageVector.vectorResource(id = R.drawable.baseline_spellcheck_24)
        }else null,
        trailingIconClick = {
            addViewModel.refactorKeywords()
        },
        singleLine = false
    )
}


@Composable
fun UploadData(
    addViewModel: AddViewModel
){
    CustomUploadButton(
        onClick = {
            addViewModel.refactorIdCorrectly()
            if (addViewModel.checkAllItemBeforeUploading(true)&&
                addViewModel.description.length in 30..60 &&
                addViewModel.avgMinPrice.toIntOrNull() != null &&
                addViewModel.avgMaxPrice.toIntOrNull() != null
                ) {
                addViewModel.uploadItem()
            } else {
                SnackBarManager.showMessage("Conditions Didn't Match")
            }
        },
        enabled = addViewModel.checkAllItemBeforeUploading(false),
        text = "Upload"
    )
}


@Composable
fun DeleteButton(
    addViewModel: AddViewModel
){
    CustomUploadButton(
        onClick = {
            addViewModel.deleteItem()
        },
        text = "Delete",
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.alert_yes_button_text_color),
            containerColor = colorResource(id = R.color.alert_yes_button_background_color)
        )
    )
}