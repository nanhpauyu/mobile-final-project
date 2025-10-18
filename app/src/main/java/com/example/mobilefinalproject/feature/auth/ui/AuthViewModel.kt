package com.example.mobilefinalproject.feature.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.core.network.dto.AuthLoginDto
import com.example.mobilefinalproject.core.network.dto.AuthRegisterDto
import com.example.mobilefinalproject.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun onUserNameChange(username: String) = _uiState.update { it.copy(username = username) }
    fun onEmailChange(email: String) = _uiState.update { it.copy(email = email) }
    fun onPasswordChange(password: String) = _uiState.update { it.copy(password = password) }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            withContext(Dispatchers.IO) {
                authRepository.login(AuthLoginDto(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                ))
            }
            .onSuccess {
                _uiState.update {
                    it.copy(isLogin = true, isLoading = false)
                }
            }
            .onFailure {
                _uiState.update {
                    it.copy(errorMessage = it.errorMessage, isLoading = false)
                }
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            withContext(Dispatchers.IO) {
                authRepository.register(
                    AuthRegisterDto(
                        username = _uiState.value.username,
                        password = _uiState.value.password,
                        email = _uiState.value.email
                    )
                )
            }
            .onSuccess {
                _uiState.update {
                    it.copy(isLogin = true, isLoading = false)
                }
            }
            .onFailure {
                _uiState.update {
                    it.copy(errorMessage = it.errorMessage, isLoading = false)
                }
            }
        }
    }

}