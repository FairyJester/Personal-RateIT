package com.example.personalrateit.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalrateit.R
import com.example.personalrateit.api.Specializations
import com.example.personalrateit.app.retrofit
import com.example.personalrateit.data.Specialization
import com.example.personalrateit.dto.specializations.SpecializationGetDTO
import com.example.personalrateit.exeptions.exeptions
import com.example.personalrateit.navigation.PersonalRateITAppRouter
import com.example.quickidenti.components.LoadingComponent
import com.example.quickidenti.components.SimpleLazyColumnScreen
import com.example.quickidenti.components.TextComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SpecializationsListScreen(){

    val specializationsApi = retrofit.create(Specializations::class.java)
    val rawSpecializations = remember{mutableListOf<SpecializationGetDTO>()}
    val context = LocalContext.current
    val dataReceived = remember { mutableStateOf(false) }
    CoroutineScope(Dispatchers.IO).launch {
        try {

            val specList = specializationsApi.getSpecializations()
            rawSpecializations.addAll(specList)
            dataReceived.value = true
        }catch (timeOut: SocketTimeoutException){
            Log.i("serverError", "server not found")
            exeptions(context, "server")
        }
    }

    if(dataReceived.value) {
        if(rawSpecializations.isNotEmpty()) {
            val specializations = remember { mutableListOf<Specialization>() }
            for (i in 0..<rawSpecializations.size) {
                specializations.add(
                    Specialization(
                        i,
                        rawSpecializations[i].name,
                        rawSpecializations[i].code,
                        rawSpecializations[i].seats_amount,
                        rawSpecializations[i].education_period
                    )
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(28.dp)
            ) {

                Column {
                    SimpleLazyColumnScreen(specializations)
                }
            }
        }else{
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(28.dp)
            ) {
                TextComponent(
                    value = stringResource(id = R.string.empty_specializations_list),
                    fontSize = 32,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
    else{
        LoadingComponent()
    }
    BackHandler(enabled = true) {
        PersonalRateITAppRouter.navigateTo(PersonalRateITAppRouter.historyScreenList[PersonalRateITAppRouter.lastHistoryIndex.value], false)
    }
}

@Preview
@Composable
fun SpecializationsListScreenPreview(){
    SpecializationsListScreen()
}

