package com.example.personalrateit.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalrateit.R
import com.example.personalrateit.navigation.PersonalRateITAppRouter
import com.example.quickidenti.components.TextComponent


@Composable
fun InfoScreen(personInfoList: MutableList<String>){

    val fullname = remember { mutableStateOf("") }
    val specializationName = remember { mutableStateOf("") }
    val rate = remember { mutableStateOf("") }
    val gpa = remember { mutableStateOf("") }
    val dateOfSubmissionOfDocuments = remember { mutableStateOf("") }
    try {
        fullname.value = personInfoList[0]
        specializationName.value = personInfoList[1]
        rate.value = personInfoList[2]
        gpa.value = personInfoList[3]
        dateOfSubmissionOfDocuments.value = personInfoList[4]
    }catch (noData: IndexOutOfBoundsException){
        Log.i("no applicant info", "server is out and applicant have no information")
    }

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
            TextComponent(
                value = "${stringResource(id = R.string.fullname)} ${fullname.value}",
                fontSize = 18,
                textAlign = TextAlign.Start
            )
            TextComponent(
                    value = "${stringResource(id = R.string.specialization)} ${specializationName.value}",
            fontSize = 18,
            textAlign = TextAlign.Start
            )
            TextComponent(
                value = "${stringResource(id = R.string.rate)} ${rate.value}",
                fontSize = 18,
                textAlign = TextAlign.Start
            )
            TextComponent(
                value = "${stringResource(id = R.string.gpa)} ${gpa.value}",
                fontSize = 18,
                textAlign = TextAlign.Start
            )
            TextComponent(
                value = "${stringResource(id = R.string.send_documents_date)} ${dateOfSubmissionOfDocuments.value}",
                fontSize = 18,
                textAlign = TextAlign.Start
            )
        }
    }
    BackHandler(enabled = true){
            PersonalRateITAppRouter.navigateTo(PersonalRateITAppRouter.historyScreenList[PersonalRateITAppRouter.lastHistoryIndex.value], false)
    }
}

@Composable
@Preview
fun InfoScreenPreview(){
    InfoScreen(mutableListOf("", "", "", "", ""))
}