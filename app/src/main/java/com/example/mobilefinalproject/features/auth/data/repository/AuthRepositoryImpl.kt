package com.example.mobilefinalproject.features.auth.data.repository

import android.util.Log
import com.example.mobilefinalproject.features.auth.data.dto.request.AuthLoginDto
import com.example.mobilefinalproject.features.auth.data.dto.request.AuthRegisterDto
import com.example.mobilefinalproject.features.auth.data.api.AuthService
import com.example.mobilefinalproject.features.auth.data.dto.response.AuthResponseDto
import com.example.mobilefinalproject.features.auth.domain.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {
    override suspend fun login(authLoginDto: AuthLoginDto): Result<AuthResponseDto> {
        return runCatching {
            authService.login(authLoginDto)
        }
    }

    override suspend fun register(authRegisterDto: AuthRegisterDto): Result<AuthResponseDto> {
        return runCatching {
            authService.register(authRegisterDto)
        }
    }
}