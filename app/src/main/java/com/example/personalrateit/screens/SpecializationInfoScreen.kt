package com.example.personalrateit.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalrateit.R
import com.example.personalrateit.navigation.PersonalRateITAppRouter
import com.example.personalrateit.navigation.Screen
import com.example.quickidenti.components.ButtonComponent
import com.example.quickidenti.components.TextComponent

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SpecializationInfoScreen(specialization: List<String>) {

    val specializationTitle = specialization[0]
    val specializationCode = specialization[1]
    val specializationSlots = specialization[2]
    val specializationLearningTime = specialization[3]

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(ScrollState(0)),
            verticalArrangement = Arrangement.SpaceAround){
            TextComponent(value = specializationTitle, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 32)
            TextComponent(value = specializationCode, textAlign = TextAlign.Center, fontSize = 24)
            TextComponent(value = "${stringResource(id = R.string.slots_count)} $specializationSlots", textAlign = TextAlign.Start, fontSize = 20)
            TextComponent(value = "${stringResource(id = R.string.learning_period)} $specializationLearningTime", textAlign = TextAlign.Start, fontSize = 20)

            Row(modifier = Modifier.fillMaxWidth()){
                ButtonComponent(value = stringResource(id = R.string.back),
                    modifier = Modifier
                        .width(200.dp)
                        .height(48.dp)
                        .weight(1f)
                        .padding(5.dp)) {
                    PersonalRateITAppRouter.navigateTo(Screen.SpecializationsListScreen, false)
                }
            }

        }
    }
    BackHandler(enabled = true){
        PersonalRateITAppRouter.navigateTo(PersonalRateITAppRouter.historyScreenList[PersonalRateITAppRouter.lastHistoryIndex.value], false)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SpecializationInfoScreenPreview(){
    SpecializationInfoScreen(listOf())
}