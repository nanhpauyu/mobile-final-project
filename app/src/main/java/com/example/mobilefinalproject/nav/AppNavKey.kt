package com.example.mobilefinalproject.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface AppNavKey: NavKey

@Serializable
data object Register: AppNavKey

@Serializable
data object Login: AppNavKey

@Serializable
data class Profile(val id: String? = null): AppNavKey

@Serializable
data object PostList: AppNavKey

@Serializable
data object AddPost: AppNavKey

@Serializable
data class CommentList(val postId: String, val username: String, val post: String): AppNavKey