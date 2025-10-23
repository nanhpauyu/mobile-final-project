package com.example.mobilefinalproject.core

import okhttp3.OkHttpClient

// Assuming you have a function to get your token
fun getTokenFromStorage(): String = "585b3dda-113e-4cf4-974f-fa068513fc5c"

val token = getTokenFromStorage()

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor(token)) // Add your custom interceptor
    .build()