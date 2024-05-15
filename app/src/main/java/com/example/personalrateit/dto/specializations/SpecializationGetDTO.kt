package com.example.personalrateit.dto.specializations


data class SpecializationGetDTO(
    val code: String,
    val name: String,
    val seats_amount: Int,
    val education_period: String
)
