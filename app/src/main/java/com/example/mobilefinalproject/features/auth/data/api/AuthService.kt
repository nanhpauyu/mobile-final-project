package com.example.mobilefinalproject.features.auth.data.api

import com.example.mobilefinalproject.features.auth.data.dto.request.AuthLoginDto
import com.example.mobilefinalproject.features.auth.data.dto.request.AuthRegisterDto
import com.example.mobilefinalproject.features.auth.data.dto.response.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body authLoginDto: AuthLoginDto): AuthResponseDto
    @POST("register")
    suspend fun register(@Body authRegisterDto: AuthRegisterDto): AuthResponseDto
}