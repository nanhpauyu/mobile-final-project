package com.example.mobilefinalproject.features.profile.data.repository

import com.example.mobilefinalproject.features.profile.data.dto.request.UpdateProfileRequestDto
import com.example.mobilefinalproject.features.profile.data.dto.response.ProfileResponseDto
import com.example.mobilefinalproject.features.profile.data.api.ProfileService
import com.example.mobilefinalproject.features.profile.domain.ProfileRepository

class ProfileRepositoryImpl(
    private val profileService: ProfileService
): ProfileRepository  {
    override suspend fun findById(id: Long): Result<ProfileResponseDto> {
        return runCatching {
            profileService.findById(id)
        }
    }

    override suspend fun editProfile(profileRequestDto: UpdateProfileRequestDto): Result<ProfileResponseDto> {
        return runCatching {
            profileService.editProfile(profileRequestDto)
        }
    }
}