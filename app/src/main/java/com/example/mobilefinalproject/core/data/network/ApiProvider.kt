package com.example.mobilefinalproject.core.data.network

import com.example.mobilefinalproject.features.auth.data.api.AuthService
import com.example.mobilefinalproject.features.profile.data.api.ProfileService
import com.example.mobilefinalproject.features.comment.data.remote.api.CommentAPIService
import com.example.mobilefinalproject.features.post.data.remote.api.PostAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java
import androidx.compose.runtime.getValue

object ApiProvider {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
//            .baseUrl("http://localhost:8080/api/v1/")
//            .baseUrl("http://172.17.141.86:8080/api/v1/")
//            .baseUrl("https://2a362ebae9b9.ngrok-free.app/api/v1/")
            .baseUrl("https://biursite-6z6n.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val profileService: ProfileService by lazy {
        retrofit.create(ProfileService::class.java)
    }

    val postAPIService: PostAPIService by lazy {
        retrofit.create(PostAPIService::class.java)
    }

    val commentAPIService: CommentAPIService by lazy {
        retrofit.create(CommentAPIService::class.java)
    }
}
