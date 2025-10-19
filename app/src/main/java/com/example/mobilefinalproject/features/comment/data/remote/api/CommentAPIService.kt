package com.example.mobilefinalproject.features.comment.data.remote.api

import com.example.mobilefinalproject.features.comment.data.remote.dto.request.CommentRequestDto
import com.example.mobilefinalproject.features.comment.data.remote.dto.response.CommentListResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentAPIService {
//    @GET("{}/comments")
//    suspend fun getComments(): CommentListResponseDto
//    @GET("{postId}/comments")
//    suspend fun getComments(@Path("postId") postId: String): CommentListResponseDto


    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId: String): CommentListResponseDto

    @POST("posts/{postId}/comments")
    suspend fun createComment(@Path("postId") postId: String,@Body commentRequestDto: CommentRequestDto): Unit

}