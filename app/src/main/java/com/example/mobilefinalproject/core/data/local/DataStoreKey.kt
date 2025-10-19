package com.example.mobilefinalproject.core.data.local

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKey {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val ID = longPreferencesKey("id")
    val USERNAME = stringPreferencesKey("username")
    val EMAIL = stringPreferencesKey("email")
    val BIO = stringPreferencesKey("bio")
}