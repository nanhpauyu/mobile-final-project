package com.example.mobilefinalproject.features.post.repository

import com.example.mobilefinalproject.features.post.data.remote.dto.request.PostRequestDto
import com.example.mobilefinalproject.features.post.data.remote.dto.response.PostListResponseDto

interface PostRepository {
    suspend fun getPosts(): Result<PostListResponseDto>
//    suspend fun getPostByID(id: String): Result<Post>
    suspend fun createPost(postRequestDto: PostRequestDto): Result<Unit>
//    suspend fun deletePost(id: String): Result<Unit>
}