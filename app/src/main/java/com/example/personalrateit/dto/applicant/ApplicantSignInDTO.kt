package com.example.personalrateit.dto.applicant

data class ApplicantSignInDTO(
    val full_name: String,
    val selected_specialities: List<Any>,
    val inn: String,
    val gpa: Float,
    val application_date: String,
    val rating: String
)
