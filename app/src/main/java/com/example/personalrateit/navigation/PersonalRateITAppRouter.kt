package com.example.personalrateit.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

sealed class Screen{

    data object SignInScreen: Screen()
    data object InfoScreen: Screen()
    data object SpecializationsListScreen: Screen()
    data object SpecializationInfoScreen: Screen()

}

object PersonalRateITAppRouter{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignInScreen)
    var historyScreenList: MutableList<Screen> = mutableListOf()
    var previewScreen: MutableState<Screen> = mutableStateOf(Screen.SignInScreen)
    var lastHistoryIndex: MutableState<Int> = mutableIntStateOf(-1)
    fun navigateTo(destination: Screen, nextOrBack: Boolean){
        if(nextOrBack){
            previewScreen.value = currentScreen.value
            historyScreenList.add(currentScreen.value)
            lastHistoryIndex.value++
        }else{
            if(historyScreenList.isNotEmpty())
            {historyScreenList.remove(previewScreen.value)}
            if (lastHistoryIndex.value != -1)
            {lastHistoryIndex.value--}
        }
        currentScreen.value = destination
    }

}