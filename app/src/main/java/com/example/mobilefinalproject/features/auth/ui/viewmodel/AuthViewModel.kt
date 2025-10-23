package com.example.mobilefinalproject.features.auth.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.core.model.CurrentUser
import com.example.mobilefinalproject.features.auth.data.dto.request.AuthLoginDto
import com.example.mobilefinalproject.features.auth.data.dto.request.AuthRegisterDto
import com.example.mobilefinalproject.features.auth.domain.AuthRepository
import com.example.mobilefinalproject.features.auth.ui.state.AuthUiState
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
    fun onBioChange(bio: String) = _uiState.update { it.copy(bio = bio) }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            withContext(Dispatchers.IO) {
                authRepository.login(
                    AuthLoginDto(
                        email = _uiState.value.email,
                        password = _uiState.value.password
                    )
                )
            }
            .onSuccess { authResponseDto ->
                _uiState.update {
                    it.copy(
                        userDetail = CurrentUser(
                            accessToken = authResponseDto.data.access_token,
                            bio = authResponseDto.data.bio,
                            email = authResponseDto.data.email,
                            id = authResponseDto.data.id,
                            username = authResponseDto.data.username
                        ),
                        isLoading = false,
                    )
                }
            }
            .onFailure { err ->
                _uiState.update {
                    it.copy(errorMessage = err.message, isLoading = false)
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
                        email = _uiState.value.email,
                        bio = _uiState.value.bio
                    )
                )
            }
            .onSuccess { authResponseDto ->
                _uiState.update {
                    it.copy(
                        userDetail = CurrentUser(
                            accessToken = authResponseDto.data.access_token,
                            bio = authResponseDto.data.bio,
                            email = authResponseDto.data.email,
                            id = authResponseDto.data.id,
                            username = authResponseDto.data.username
                        ),
                        isLoading = false,
                    )
                }
            }
            .onFailure { err ->
                _uiState.update {
                    it.copy(errorMessage = err.message, isLoading = false)
                }
            }
        }
    }
}