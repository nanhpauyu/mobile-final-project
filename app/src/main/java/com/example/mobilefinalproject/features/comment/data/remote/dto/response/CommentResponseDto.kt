package com.example.mobilefinalproject.features.comment.data.remote.dto.response

data class CommentResponseDto(
    val id: String,
    val userId: String,
    val username: String,
    val userProfile:String,
    val text: String,
    val createdDate: String
)
