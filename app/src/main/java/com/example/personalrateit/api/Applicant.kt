package com.example.personalrateit.api

import com.example.personalrateit.dto.applicant.ApplicantSignInDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface Applicant {
    @GET("/api/enrollees/{snils}")
    suspend fun signIn(@Path("snils") snils: String): ApplicantSignInDTO
}