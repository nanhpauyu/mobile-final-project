package com.example.mobilefinalproject.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.core.domain.CurrentUserRepository
import com.example.mobilefinalproject.core.model.CurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrentUserViewModel(
    private val currentUserRepository: CurrentUserRepository
): ViewModel() {
    private val _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            currentUserRepository.getUserCredentials().collect { user ->
                if (user != null) {
                    _currentUser.update { user }
                }
            }
        }
    }

    fun saveCurrentUser(user: CurrentUser) {
        viewModelScope.launch {
            currentUserRepository.saveCurrentUser(user)
        }
    }
}