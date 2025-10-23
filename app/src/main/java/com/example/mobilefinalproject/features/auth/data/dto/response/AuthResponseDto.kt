package com.example.mobilefinalproject.features.auth.data.dto.response

data class AuthResponseDto(
    val data: DataDto,
    val status: String

)

data class DataDto(
    val access_token: String,
    val bio: String,
    val email: String,
    val id: String,
    val username: String
)

