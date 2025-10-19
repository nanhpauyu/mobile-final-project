package com.example.mobilefinalproject.feature.profile.data.repository

import com.example.mobilefinalproject.core.data.network.dto.ProfileRequestDto
import com.example.mobilefinalproject.core.data.network.dto.ProfileResponseDto
import com.example.mobilefinalproject.feature.profile.data.api.ProfileService
import com.example.mobilefinalproject.feature.profile.domain.ProfileRepository

class ProfileRepositoryImpl(
    private val profileService: ProfileService
): ProfileRepository  {
    override suspend fun me(): Result<ProfileResponseDto> {
        return runCatching {
            profileService.me()
        }
    }

    override suspend fun findById(id: Long): Result<ProfileResponseDto> {
        return runCatching {
            profileService.findById(id)
        }
    }

    override suspend fun editProfile(profileRequestDto: ProfileRequestDto): Result<Unit> {
        return runCatching {
            profileService.editProfile(profileRequestDto)
        }
    }
}