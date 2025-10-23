package com.example.mobilefinalproject.features.comment.ui.commentlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.features.comment.data.remote.dto.request.CommentRequestDto
import com.example.mobilefinalproject.features.comment.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentCreateViewModel(
    private val commentRepository: CommentRepository,
    private val postId: String
) : ViewModel() {

    private val _commentCreateUIState = MutableStateFlow(CommentCreateUIState(postId = postId))
    val commentCreateUIState = _commentCreateUIState

    fun updateComment(text: String) {
        _commentCreateUIState.update {
            it.copy(text = text)

        }
    }

    fun resetCreateCommentUIState() = _commentCreateUIState.update { CommentCreateUIState.EMPTY }

    fun createComment() {
        viewModelScope.launch {
            _commentCreateUIState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val result = withContext(Dispatchers.IO) {
                commentRepository.createComment(_commentCreateUIState.value.postId,
                    CommentRequestDto(_commentCreateUIState.value.text,"711d5724-d547-3665-925b-73c3f697a758","Hpauyu Nan")
                )
            }
            result
                .onSuccess {
                    _commentCreateUIState.update {
                        it.copy(isLoading = false, errorMessage = null, isSuccess = true)
                    }
                }
                .onFailure { error ->
                    _commentCreateUIState.update {
                        it.copy(isLoading = false, errorMessage = error.message, isSuccess = false)
                    }
                }

        }
    }

}