package com.example.mobilefinalproject.features.profile.ui.state

data class ProfileUiState(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val bio: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)