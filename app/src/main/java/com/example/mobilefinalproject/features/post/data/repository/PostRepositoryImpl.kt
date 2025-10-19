package com.example.mobilefinalproject.features.post.data.repository

import com.example.mobilefinalproject.features.post.data.remote.api.PostAPIService
import com.example.mobilefinalproject.features.post.data.remote.dto.request.PostRequestDto
import com.example.mobilefinalproject.features.post.data.remote.dto.response.PostListResponseDto
import com.example.mobilefinalproject.features.post.repository.PostRepository

class PostRepositoryImpl(
    private val postAPIService: PostAPIService
) : PostRepository {
    override suspend fun getPosts(): Result<PostListResponseDto> {
        return runCatching {
            postAPIService.getPosts()
        }
    }

//    override suspend fun getPostByID(id: String): Result<Post> {
//        TODO("Not yet implemented")
//    }
//
    override suspend fun createPost(postRequestDto: PostRequestDto): Result<Unit> {
        return runCatching {
            postAPIService.createPost(postRequestDto)
        }
    }
//
//    override suspend fun deletePost(id: String): Result<Unit> {
//        TODO("Not yet implemented")
//    }
}