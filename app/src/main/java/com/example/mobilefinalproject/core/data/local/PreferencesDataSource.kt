package com.example.mobilefinalproject.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import com.example.mobilefinalproject.core.model.CurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSource(
    private val context: Context
) {
    suspend fun saveCurrentUser(user: CurrentUser) {
        context.dataStore.edit { preferences: MutablePreferences ->
            preferences[DataStoreKey.ID] = user.id
            preferences[DataStoreKey.USERNAME] = user.username
            preferences[DataStoreKey.EMAIL] = user.email
            preferences[DataStoreKey.BIO] = user.bio
            preferences[DataStoreKey.ACCESS_TOKEN] = user.accessToken
        }
    }

    fun getCurrentUser(): Flow<CurrentUser?> {
        return context.dataStore.data.map { preferences ->

            if (preferences[DataStoreKey.ACCESS_TOKEN].isNullOrEmpty()) {
                null
            } else  {
                CurrentUser(
                    id = preferences[DataStoreKey.ID]!!,
                    username = preferences[DataStoreKey.USERNAME]!!,
                    email = preferences[DataStoreKey.EMAIL]!!,
                    bio = preferences[DataStoreKey.BIO]!!,
                    accessToken = preferences[DataStoreKey.ACCESS_TOKEN]!!,
                )
            }
        }
    }
}
