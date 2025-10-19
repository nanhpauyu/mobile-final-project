package com.example.mobilefinalproject.core.repository

import com.example.mobilefinalproject.core.data.local.PreferencesDataSource
import com.example.mobilefinalproject.core.domain.CurrentUserRepository
import com.example.mobilefinalproject.core.model.CurrentUser
import kotlinx.coroutines.flow.Flow

class CurrentUserRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource
): CurrentUserRepository {
    override suspend fun saveCurrentUser(user: CurrentUser): Result<Unit> {
        return runCatching {
            preferencesDataSource.saveCurrentUser(user)
        }
    }

    override fun getUserCredentials(): Flow<CurrentUser?> = preferencesDataSource.getCurrentUser()
}