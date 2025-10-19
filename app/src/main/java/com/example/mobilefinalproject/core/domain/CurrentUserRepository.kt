package com.example.mobilefinalproject.core.domain

import com.example.mobilefinalproject.core.model.CurrentUser
import kotlinx.coroutines.flow.Flow

interface CurrentUserRepository {
    suspend fun saveCurrentUser(user: CurrentUser) : Result<Unit>
    fun getUserCredentials(): Flow<CurrentUser?>
}