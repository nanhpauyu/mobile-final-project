package com.example.mobilefinalproject.features.profile.data.dto.response
data class ProfileResponseDto(
    val data: ProfileDataDto,
    val status: String
)

data class ProfileDataDto(
    val bio: String,
    val email: String,
    val id: String,
    val password: String,
    val username: String
)
