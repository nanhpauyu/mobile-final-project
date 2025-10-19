package com.example.mobilefinalproject.feature.profile.ui.state

data class ProfileUiState(
    val id: Long = 0,
    val username: String = "",
    val email: String = "",
    val bio: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)