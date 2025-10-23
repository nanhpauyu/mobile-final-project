package com.example.mobilefinalproject.features.auth.ui.state

import com.example.mobilefinalproject.core.model.CurrentUser

data class AuthUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val bio: String = "",
    val userDetail: CurrentUser? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)