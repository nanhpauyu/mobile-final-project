package com.example.mobilefinalproject.features.comment.ui.commentlist

data class CommentCreateUIState(
    val text: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean? = null,
    val postId: String = ""
) {
    companion object {
        val EMPTY = CommentCreateUIState()
    }
}
