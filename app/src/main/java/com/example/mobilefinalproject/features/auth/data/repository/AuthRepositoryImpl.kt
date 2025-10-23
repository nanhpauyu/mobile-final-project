package com.example.mobilefinalproject.features.auth.data.repository

import android.util.Log
import com.example.mobilefinalproject.core.data.network.dto.AuthLoginDto
import com.example.mobilefinalproject.core.data.network.dto.AuthRegisterDto
import com.example.mobilefinalproject.features.auth.data.api.AuthService
import com.example.mobilefinalproject.features.auth.domain.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {
    override suspend fun login(authLoginDto: AuthLoginDto): Result<Unit> {
        return runCatching {
            Log.i("AuthRepositoryImpl.login", "login-called ${authLoginDto.toString()}")
            authService.login(authLoginDto)
            Log.i("AuthRepositoryImpl.login", "after login-called ${authLoginDto.toString()}")
        }
    }

    override suspend fun register(authRegisterDto: AuthRegisterDto): Result<Unit> {
        return runCatching {
            authService.register(authRegisterDto)
        }
    }
}