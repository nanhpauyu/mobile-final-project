package com.example.mobilefinalproject.features.post.data.remote.dto.response

data class PostResponseDto(
    val id:String,
    val userId:String,
    val username: String,
    val userProfile:String,
    val text:String,
    val imageUrl:String,
    val createdDate:String,
)