package com.example.mobilefinalproject.core

import android.content.Context
import com.example.mobilefinalproject.core.data.local.PreferencesDataSource
import com.example.mobilefinalproject.core.repository.CurrentUserRepositoryImpl

object AppStateProvider {
    var appState: AppState? = null

    fun getAppState(context: Context): AppState {
        return appState ?: synchronized(this) {
            appState ?: run {
                appState = AppState(
                    CurrentUserRepositoryImpl(
                        PreferencesDataSource(context)
                    )
                )
                appState!!
            }
        }
    }
}