package com.example.mobilefinalproject.features.profile.domain

import com.example.mobilefinalproject.core.data.network.dto.ProfileRequestDto
import com.example.mobilefinalproject.core.data.network.dto.ProfileResponseDto

interface ProfileRepository {
    suspend fun me(): Result<ProfileResponseDto>
    suspend fun findById(id: Long): Result<ProfileResponseDto>
    suspend fun editProfile(profileRequestDto: ProfileRequestDto): Result<Unit>
}