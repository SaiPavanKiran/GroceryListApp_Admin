package com.rspk.adminapp.compose.home

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rspk.adminapp.R
import com.rspk.adminapp.common.composables.CustomDropDownMenu
import com.rspk.adminapp.common.composables.CustomDropDownTextField
import com.rspk.adminapp.common.composables.CustomHeading
import com.rspk.adminapp.common.composables.CustomTextButton
import com.rspk.adminapp.common.composables.CustomTextFieldWithHeading
import com.rspk.adminapp.common.composables.CustomUploadButton
import com.rspk.adminapp.common.snackbar.SnackBarManager
import com.rspk.adminapp.constants.CurrentButtonLoading
import com.rspk.adminapp.constants.mappedTypeToSubType
import com.rspk.adminapp.constants.type_Item_List
import com.rspk.adminapp.navigation.NavigationRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddScreen(
    navigate:(NavigationRoutes) -> Unit,
    clearAndNavigate:(NavigationRoutes) -> Unit,
    addViewModel: AddViewModel = hiltViewModel(),
) {
    if(addViewModel.currentButtonLoading == CurrentButtonLoading.NONE) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 25.dp, vertical = 5.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextButton(
                text = "Resolve Customer Issues??",
                onIconButtonClick = {
                    addViewModel.signOut(clearAndNavigate)
                },
                onClick = {
                navigate(NavigationRoutes.CustomerService)
                }
            )
            Id(addViewModel = addViewModel)
            Description(addViewModel = addViewModel)
            TypeAndSubType(addViewModel = addViewModel)
            ImagePreview(addViewModel = addViewModel)
            MinAndMaxPrice(addViewModel = addViewModel)
            QuantityInAndStarCount(addViewModel = addViewModel)
            SearchKeywords(addViewModel = addViewModel)
            UploadData(addViewModel = addViewModel)
            if(addViewModel.idReadOnly && addViewModel.itemFound)
            DeleteButton(addViewModel = addViewModel)
        }
    }else if(addViewModel.currentButtonLoading == CurrentButtonLoading.UPLOADING){
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.uploading))
        LottieAnimationLayout(
            composition = composition
        )
    }else if(addViewModel.currentButtonLoading == CurrentButtonLoading.DELETING){
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.deleting))
        LottieAnimationLayout(
            composition = composition
        )
    }
}



@Composable
fun LottieAnimationLayout(
    composition: LottieComposition?
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(500.dp),
            iterations = LottieConstants.IterateForever
        )
    }
}



