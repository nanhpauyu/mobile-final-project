package com.example.mobilefinalproject.core.model

data class Post(
    val id:String,
    val userId:String,
    val username: String,
    val userProfile:String,
    val text:String,
    val imageUrl:String,
    val createdDate:String,
)
