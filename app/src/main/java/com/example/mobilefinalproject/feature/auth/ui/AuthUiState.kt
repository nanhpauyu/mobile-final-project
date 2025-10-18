package com.example.mobilefinalproject.feature.auth.ui

data class AuthUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLogin: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
