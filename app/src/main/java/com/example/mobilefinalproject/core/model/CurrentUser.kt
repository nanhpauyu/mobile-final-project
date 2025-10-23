package com.example.mobilefinalproject.core.model

data class CurrentUser(
    val id: String,
    val username: String,
    val email: String,
    val bio: String,
    val accessToken: String,
)
