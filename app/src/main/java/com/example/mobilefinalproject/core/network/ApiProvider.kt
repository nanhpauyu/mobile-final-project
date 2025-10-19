package com.example.mobilefinalproject.core.network

import com.example.mobilefinalproject.feature.auth.data.api.AuthService
import com.example.mobilefinalproject.feature.profile.data.api.ProfileService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object ApiProvider {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
//            .baseUrl("http://localhost:8080/api/v1/")
//            .baseUrl("http://172.17.141.86:8080/api/v1/")
            .baseUrl("https://2a362ebae9b9.ngrok-free.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val profileService: ProfileService by lazy {
        retrofit.create(ProfileService::class.java)
    }
}