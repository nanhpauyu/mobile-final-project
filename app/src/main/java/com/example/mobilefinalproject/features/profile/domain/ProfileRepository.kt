package com.example.mobilefinalproject.features.profile.domain

import com.example.mobilefinalproject.features.profile.data.dto.request.UpdateProfileRequestDto
import com.example.mobilefinalproject.features.profile.data.dto.response.ProfileResponseDto

interface ProfileRepository {
    suspend fun findById(id: Long): Result<ProfileResponseDto>
    suspend fun editProfile(profileRequestDto: UpdateProfileRequestDto): Result<ProfileResponseDto>
}