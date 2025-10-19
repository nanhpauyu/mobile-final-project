package com.example.mobilefinalproject.feature.auth.domain

import com.example.mobilefinalproject.core.data.network.dto.AuthLoginDto
import com.example.mobilefinalproject.core.data.network.dto.AuthRegisterDto

interface AuthRepository {
    suspend fun login(authLoginDto: AuthLoginDto): Result<Unit>
    suspend fun register(authRegisterDto: AuthRegisterDto): Result<Unit>
}