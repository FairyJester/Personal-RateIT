package com.example.personalrateit.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalrateit.R
import com.example.personalrateit.api.Applicant
import com.example.personalrateit.app.retrofit
import com.example.personalrateit.exeptions.exeptions
import com.example.personalrateit.navigation.PersonalRateITAppRouter
import com.example.personalrateit.navigation.Screen
import com.example.personalrateit.operations.applicantData
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.components.TextFieldComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SignInScreen(){

    val snils = remember {mutableStateOf("")}
    val context = LocalContext.current
    val applicantApi = retrofit.create(Applicant::class.java)

    val applicantNotFound = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround){
            Column(
                verticalArrangement = Arrangement.SpaceBetween) {

                TextComponent(
                    value = stringResource(id = R.string.snils_input),
                    fontSize = 32,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp))
                TextFieldComponent(
                    labelValue = stringResource(id = R.string.snils),
                    textValue = snils.value,
                    painterResource = null,
                    onValueChange = { snils.value = it }
                )

            }
            ButtonComponent(value = stringResource(id = R.string.to_know_rate), modifier = Modifier
                .width(200.dp)
                .height(100.dp)) {
                if(snils.value != ""){
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            try {
                                val applicantData = applicantApi.signIn(snils.value)
                                applicantData(applicantData)
                                PersonalRateITAppRouter.navigateTo(Screen.InfoScreen, true)
                            }catch (notFound: retrofit2.HttpException){
                                applicantNotFound.value = true
                                Log.i("applicant_not_found", "applicant not found")
                                exeptions(context, "applicant_not_found")
                            }
                        } catch (timeOut: SocketTimeoutException) {
                            Log.i("serverError", "server not found")
                            exeptions(context, "server")
                            PersonalRateITAppRouter.navigateTo(Screen.InfoScreen, true)
                        }
                    }

                }else{
                    exeptions(context, "havent_snils")
                }
            }
            ButtonComponent(value = stringResource(id = R.string.specializations_list) , modifier = Modifier
                .width(250.dp)
                .height(80.dp)) {
                    PersonalRateITAppRouter.navigateTo(Screen.SpecializationsListScreen, true)
            }
        }
    }
    BackHandler(enabled = true){
        if(PersonalRateITAppRouter.historyScreenList.isNotEmpty())
            PersonalRateITAppRouter.navigateTo(PersonalRateITAppRouter.historyScreenList[PersonalRateITAppRouter.lastHistoryIndex.value], false)
    }
}


@Composable
@Preview
fun SignInScreenPreview(){
    SignInScreen()
}