package com.example.mobilefinalproject.feature.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.core.network.dto.ProfileRequestDto
import com.example.mobilefinalproject.feature.profile.domain.ProfileRepository
import com.example.mobilefinalproject.feature.profile.ui.state.EditProfileUiState
import com.example.mobilefinalproject.feature.profile.ui.state.ProfileUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {
    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()
    private val _editProfileUiState = MutableStateFlow(EditProfileUiState())
    val editProfileUiState = _editProfileUiState.asStateFlow()

    fun onUsernameChange(username: String) = _editProfileUiState.update { it.copy(username = username) }
    fun onBioChange(bio: String) = _editProfileUiState.update { it.copy(bio = bio) }


    fun getMyProfile() {
        viewModelScope.launch {
            _profileUiState.update {
                it.copy(isLoading = true)
            }
            withContext(Dispatchers.IO) {
                profileRepository.me()
            }
            .onSuccess { profile ->
                _profileUiState.update {
                    it.copy(
                        id = profile.id,
                        username = profile.username,
                        email = profile.email,
                        bio = profile.bio,
                        isLoading = false,
                        errorMessage = null,
                    )
                }
            }
            .onFailure { err ->
                _profileUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message,
                    )
                }
            }
        }
    }

    fun editProfile() {
        viewModelScope.launch {
            _editProfileUiState.update {
                it.copy(isLoading = true)
            }
            withContext(Dispatchers.IO) {
                profileRepository.editProfile(
                    ProfileRequestDto(
                        username = _editProfileUiState.value.username,
                        bio = _editProfileUiState.value.bio,
                    )
                )
            }
            .onSuccess {
                _editProfileUiState.update {
                    it.copy(isLoading = false)
                }
                _profileUiState.update {
                    it.copy(
                        username = _editProfileUiState.value.username,
                        bio = _editProfileUiState.value.bio,
                    )
                }
            }
            .onFailure { err ->
                _editProfileUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message,
                    )
                }
            }
        }
    }

    fun getProfileById(id: Long) {
        viewModelScope.launch {
            _profileUiState.update {
                it.copy(isLoading = true)
            }
            withContext(Dispatchers.IO) {
                profileRepository.findById(id)
            }
            .onSuccess { profile ->
                _profileUiState.update {
                    it.copy(
                        id = profile.id,
                        username = profile.username,
                        email = profile.email,
                        bio = profile.bio,
                        isLoading = false,
                        errorMessage = null,
                    )
                }
            }
            .onFailure { err ->
                _profileUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message,
                    )
                }
            }
        }
    }
}