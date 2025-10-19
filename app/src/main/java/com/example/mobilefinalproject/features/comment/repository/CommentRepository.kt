package com.example.mobilefinalproject.features.comment.repository

import com.example.mobilefinalproject.features.comment.data.remote.dto.request.CommentRequestDto
import com.example.mobilefinalproject.features.comment.data.remote.dto.response.CommentListResponseDto

interface CommentRepository {
    suspend fun getComments(postId: String):Result<CommentListResponseDto>
    suspend fun createComment(postId: String,commentRequestDto: CommentRequestDto):Result<Unit>
}