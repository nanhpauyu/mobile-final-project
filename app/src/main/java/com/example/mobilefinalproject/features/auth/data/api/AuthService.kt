package com.example.mobilefinalproject.features.auth.data.api

import com.example.mobilefinalproject.core.data.network.dto.AuthLoginDto
import com.example.mobilefinalproject.core.data.network.dto.AuthRegisterDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body authLoginDto: AuthLoginDto)
    @POST("register")
    suspend fun register(@Body authRegisterDto: AuthRegisterDto)
}