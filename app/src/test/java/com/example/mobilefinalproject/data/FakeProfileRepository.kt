package com.example.mobilefinalproject.data

import com.example.mobilefinalproject.features.profile.data.dto.request.UpdateProfileRequestDto
import com.example.mobilefinalproject.features.profile.data.dto.response.ProfileDataDto
import com.example.mobilefinalproject.features.profile.data.dto.response.ProfileResponseDto
import com.example.mobilefinalproject.features.profile.domain.ProfileRepository

class FakeProfileRepository: ProfileRepository {
    override suspend fun findById(id: String): Result<ProfileResponseDto> {
        return Result.success(
            ProfileResponseDto(
                data = ProfileDataDto(
                    id = id,
                    username = "foo",
                    bio = "my bio",
                    email = "f@mail.com",
                ),
                status = "success"
                )
            )
    }

    override suspend fun editProfile(profileRequestDto: UpdateProfileRequestDto): Result<ProfileResponseDto> {
        return Result.success(
            ProfileResponseDto(
                data = ProfileDataDto(
                    id = "1",
                    username = profileRequestDto.username,
                    bio = profileRequestDto.bio,
                    email = "f@mail.com",
                ),
                status = "success"
            )
        )
    }
}