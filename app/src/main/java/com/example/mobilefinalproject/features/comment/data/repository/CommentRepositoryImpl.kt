package com.example.mobilefinalproject.features.comment.data.repository

import com.example.mobilefinalproject.features.comment.data.remote.api.CommentAPIService
import com.example.mobilefinalproject.features.comment.data.remote.dto.request.CommentRequestDto
import com.example.mobilefinalproject.features.comment.data.remote.dto.response.CommentListResponseDto
import com.example.mobilefinalproject.features.comment.repository.CommentRepository

class CommentRepositoryImpl(
    private val commentAPIService: CommentAPIService
) : CommentRepository {
    override suspend fun getComments(postId: String): Result<CommentListResponseDto> {
        return runCatching {
            commentAPIService.getComments(postId)
        }
    }

    override suspend fun createComment(
        postId: String,
        commentRequestDto: CommentRequestDto
    ): Result<Unit> {
        return runCatching {
            commentAPIService.createComment(postId, commentRequestDto)
        }
    }
}