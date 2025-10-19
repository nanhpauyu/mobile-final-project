package com.example.mobilefinalproject.feature.profile.data.api

import com.example.mobilefinalproject.core.network.dto.ProfileRequestDto
import com.example.mobilefinalproject.core.network.dto.ProfileResponseDto
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileService {
    @GET("/users/me")
    suspend fun me(): ProfileResponseDto
    @GET("/users/{id}")
    suspend fun findById(id: Long): ProfileResponseDto
    @POST("/users/me")
    suspend fun editProfile(profileRequestDto: ProfileRequestDto)
}