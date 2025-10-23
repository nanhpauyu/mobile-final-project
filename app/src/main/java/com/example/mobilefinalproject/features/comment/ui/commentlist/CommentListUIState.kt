package com.example.mobilefinalproject.features.comment.ui.commentlist

import com.example.mobilefinalproject.features.comment.data.remote.dto.response.CommentListResponseDto

data class CommentListUIState(
    val isLoading: Boolean = false,
    val comments: CommentListResponseDto? = null,
    val errorMessage: String? = null,
    val username: String = "",
    val userId: String = "",
    val post: String = ""
)