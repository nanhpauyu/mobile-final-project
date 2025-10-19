package com.example.mobilefinalproject.core.viewmodel

import android.content.Context
import com.example.mobilefinalproject.core.data.local.PreferencesDataSource
import com.example.mobilefinalproject.core.repository.CurrentUserRepositoryImpl

object AppViewModelProvider {
    var currentUserViewModel: CurrentUserViewModel? = null

    fun getCurrentUserViewModel(context: Context): CurrentUserViewModel {
        return currentUserViewModel ?: synchronized(this) {
            currentUserViewModel ?: run {
                currentUserViewModel = CurrentUserViewModel(
                    CurrentUserRepositoryImpl(
                        PreferencesDataSource(context)
                    )
                )
                currentUserViewModel!!
            }
        }
    }
}