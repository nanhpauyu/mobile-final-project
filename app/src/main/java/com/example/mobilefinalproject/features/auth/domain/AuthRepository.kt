package com.example.mobilefinalproject.features.auth.domain

import com.example.mobilefinalproject.features.auth.data.dto.request.AuthLoginDto
import com.example.mobilefinalproject.features.auth.data.dto.request.AuthRegisterDto
import com.example.mobilefinalproject.features.auth.data.dto.response.AuthResponseDto

interface AuthRepository {
    suspend fun login(authLoginDto: AuthLoginDto): Result<AuthResponseDto>
    suspend fun register(authRegisterDto: AuthRegisterDto): Result<AuthResponseDto>
}