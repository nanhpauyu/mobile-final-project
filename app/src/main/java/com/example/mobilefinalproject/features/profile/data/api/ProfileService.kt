package com.example.mobilefinalproject.features.profile.data.api

import com.example.mobilefinalproject.features.profile.data.dto.request.UpdateProfileRequestDto
import com.example.mobilefinalproject.features.profile.data.dto.response.ProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileService {
    @GET("users/{id}")
    suspend fun findById(@Path("id") id: String): ProfileResponseDto
    @POST("update_profile")
    suspend fun editProfile(@Body profileRequestDto: UpdateProfileRequestDto): ProfileResponseDto
}