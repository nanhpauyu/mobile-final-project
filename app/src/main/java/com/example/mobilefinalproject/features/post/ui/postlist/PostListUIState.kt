package com.example.mobilefinalproject.features.post.ui.postlist

import com.example.mobilefinalproject.features.post.data.remote.dto.response.PostListResponseDto

data class PostListUIState(
    val posts: PostListResponseDto?= null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)