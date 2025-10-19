package com.example.mobilefinalproject.feature.auth.data.api

import com.example.mobilefinalproject.core.network.dto.AuthLoginDto
import com.example.mobilefinalproject.core.network.dto.AuthRegisterDto
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(authLoginDto: AuthLoginDto)
    @POST("/users")
    suspend fun register(authRegisterDto: AuthRegisterDto)
}