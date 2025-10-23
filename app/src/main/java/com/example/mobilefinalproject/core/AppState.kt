package com.example.mobilefinalproject.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.core.domain.CurrentUserRepository
import com.example.mobilefinalproject.core.model.CurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppState(
    private val currentUserRepository: CurrentUserRepository
): ViewModel() {
//    private val _currentUser = MutableStateFlow<CurrentUser?>(null)
//    val currentUser = _currentUser.asStateFlow()
//
//    init {
//        Log.i("AppState", "init")
//        viewModelScope.launch {
//            currentUserRepository.getUserCredentials().collect { user ->
//                _currentUser.update { user }
//            }
//        }
//    }

    val currentUser: StateFlow<CurrentUser?>  = currentUserRepository.getUserCredentials()
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .onStart { null }
        .map { user: CurrentUser? ->  user}
        .catch { exception -> emit(null) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun saveCurrentUser(user: CurrentUser) {
        viewModelScope.launch {
            currentUserRepository.saveCurrentUser(user)
        }
    }
}