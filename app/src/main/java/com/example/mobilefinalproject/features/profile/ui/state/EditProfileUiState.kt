package com.example.mobilefinalproject.features.profile.ui.state

data class EditProfileUiState (
    val id: Long = 0,
    val username: String = "",
    val bio: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)