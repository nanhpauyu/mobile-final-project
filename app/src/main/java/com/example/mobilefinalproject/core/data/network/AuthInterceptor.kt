package com.example.mobilefinalproject.core.data.network

import android.content.Context
import com.example.mobilefinalproject.core.AppStateProvider
import com.example.mobilefinalproject.core.model.CurrentUser
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val appState = AppStateProvider.getAppState(context)
        val currentUser: CurrentUser? = appState.currentUser.value

        val requestBuilder = chain.request().newBuilder()
        currentUser?.let {
            requestBuilder.addHeader("Authorization", "Bearer ${currentUser.accessToken}")
        }
        return chain.proceed(requestBuilder.build())
    }
}
