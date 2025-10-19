package com.example.mobilefinalproject.core.network

import com.example.mobilefinalproject.features.comment.data.remote.api.CommentAPIService
import com.example.mobilefinalproject.features.post.data.remote.api.PostAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIProvider {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://biursite-6z6n.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val postAPIService: PostAPIService by lazy {
        retrofit.create(PostAPIService::class.java)
    }

    val commentAPIService: CommentAPIService by lazy {
        retrofit.create(CommentAPIService::class.java)
    }

}