package com.example.personalrateit.app

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalrateit.R
import com.example.personalrateit.navigation.PersonalRateITAppRouter
import com.example.personalrateit.navigation.Screen
import com.example.personalrateit.screens.InfoScreen
import com.example.personalrateit.screens.SignInScreen
import com.example.personalrateit.screens.SpecializationInfoScreen
import com.example.personalrateit.screens.SpecializationsListScreen
import com.example.quickidenti.components.TextComponent
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.White
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient().newBuilder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(10, TimeUnit.SECONDS)
    .build()
val retrofit = Retrofit.Builder()
    .baseUrl("http:/10.0.2.2:8000")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()
val clickedSpecialization = mutableIntStateOf(-1)
val personInfo = mutableListOf<String>()
var specializationInfo = listOf<String>()
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalRateITApp() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Column(modifier= Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(color = Primary)) {
                Spacer(modifier= Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(color = White))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp)) {
                    Image(
                        modifier = Modifier
                            .height(100.dp)
                            .width(110.dp),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logotype"
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(30.dp)
                    )
                    Column(modifier = Modifier
                        .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround) {
                        TextComponent(
                            value = stringResource(id = R.string.your_rate),
                            fontSize = 32,
                            fontWeight = FontWeight.Bold,
                            color = White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White
        ) {

            Crossfade(
                targetState = PersonalRateITAppRouter.currentScreen,
                label = ""
            ) { currentState ->
                when (currentState.value) {
                    is Screen.SignInScreen -> {
                        SignInScreen()
                    }

                    is Screen.InfoScreen -> {
                        InfoScreen(personInfo)
                    }

                    is Screen.SpecializationsListScreen -> {
                        SpecializationsListScreen()
                    }

                    is Screen.SpecializationInfoScreen -> {
                        SpecializationInfoScreen(specializationInfo)
                    }


                }
            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun PreviewApp(){
    PersonalRateITApp()
}