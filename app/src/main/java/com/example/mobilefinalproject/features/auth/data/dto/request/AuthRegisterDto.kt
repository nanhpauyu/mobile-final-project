package com.example.mobilefinalproject.features.auth.data.dto.request

data class AuthRegisterDto(
    val username: String,
    val email: String,
    val password: String,
    val bio: String,
)