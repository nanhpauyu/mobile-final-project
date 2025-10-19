package com.example.mobilefinalproject.feature.profile.domain

import com.example.mobilefinalproject.core.network.dto.ProfileRequestDto
import com.example.mobilefinalproject.core.network.dto.ProfileResponseDto

interface ProfileRepository {
    suspend fun me(): Result<ProfileResponseDto>
    suspend fun findById(id: Long): Result<ProfileResponseDto>
    suspend fun editProfile(profileRequestDto: ProfileRequestDto): Result<Unit>
}