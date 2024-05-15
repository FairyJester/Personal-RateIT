package com.example.personalrateit.operations

import androidx.compose.runtime.mutableStateOf
import com.example.personalrateit.app.personInfo
import com.example.personalrateit.dto.applicant.ApplicantSignInDTO

fun applicantData(applicantData: ApplicantSignInDTO) {

    val chosenSpeciality = mutableStateOf("")
    var count = 0
    while (chosenSpeciality.value == "") {
        val specialization =
            applicantData.selected_specialities[count].toString()
        val params = mutableListOf<String>()
        params.addAll(specialization.split(", "))
        params[0] = params[0].replace("{", "")
        if ((params[1] == "is_priority=true"))
            chosenSpeciality.value = params[0].split("=")[1]
        count++
    }
    val appDataList = listOf(
        applicantData.full_name,
        chosenSpeciality.value,
        applicantData.rating,
        applicantData.gpa.toString(),
        applicantData.application_date,
    )
    personInfo.addAll(appDataList)
}