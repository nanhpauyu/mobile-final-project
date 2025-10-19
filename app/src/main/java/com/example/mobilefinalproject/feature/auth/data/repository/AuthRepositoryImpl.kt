package com.example.mobilefinalproject.feature.auth.data.repository

import com.example.mobilefinalproject.core.data.network.dto.AuthLoginDto
import com.example.mobilefinalproject.core.data.network.dto.AuthRegisterDto
import com.example.mobilefinalproject.feature.auth.data.api.AuthService
import com.example.mobilefinalproject.feature.auth.domain.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {
    override suspend fun login(authLoginDto: AuthLoginDto): Result<Unit> {
        return runCatching {
            authService.login(authLoginDto)
        }
    }

    override suspend fun register(authRegisterDto: AuthRegisterDto): Result<Unit> {
        return runCatching {
            authService.register(authRegisterDto)
        }
    }
}