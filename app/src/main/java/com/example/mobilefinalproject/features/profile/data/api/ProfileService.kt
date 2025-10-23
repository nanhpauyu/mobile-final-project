package com.example.mobilefinalproject.features.profile.data.api

import com.example.mobilefinalproject.features.profile.data.dto.request.UpdateProfileRequestDto
import com.example.mobilefinalproject.features.profile.data.dto.response.ProfileResponseDto
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileService {
    @GET("users/{id}")
    suspend fun findById(id: Long): ProfileResponseDto
    @POST("update_profile")
    suspend fun editProfile(profileRequestDto: UpdateProfileRequestDto): ProfileResponseDto
}