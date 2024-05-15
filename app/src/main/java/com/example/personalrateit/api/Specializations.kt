package com.example.personalrateit.api

import com.example.personalrateit.dto.specializations.SpecializationGetDTO
import retrofit2.http.GET

interface Specializations {
    @GET("/api/specialities")
    suspend fun getSpecializations(): List<SpecializationGetDTO>
}