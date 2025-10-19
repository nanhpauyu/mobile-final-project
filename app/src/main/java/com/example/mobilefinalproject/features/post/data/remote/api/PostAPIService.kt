package com.example.mobilefinalproject.features.post.data.remote.api

import com.example.mobilefinalproject.features.post.data.remote.dto.request.PostRequestDto
import com.example.mobilefinalproject.features.post.data.remote.dto.response.PostListResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostAPIService {

    @GET ("posts")
    suspend fun getPosts(): PostListResponseDto

    @POST("posts")
    suspend fun createPost(@Body postRequestDto: PostRequestDto): Unit
}