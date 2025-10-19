package com.example.mobilefinalproject.features.comment.ui.commentlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.features.comment.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentListViewModel(
    private val commentRepository: CommentRepository,
    postId:String,
    username:String,
    post:String
): ViewModel() {
    private val _commentListUIState = MutableStateFlow(CommentListUIState(username=username,post=post))
    val commentListUIState= _commentListUIState.asStateFlow()

    init {
        getComments(postId)
    }

    fun getComments(postId: String){
        Log.i("postId",postId)
        viewModelScope.launch {
            _commentListUIState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val result = withContext(Dispatchers.IO) {
                commentRepository.getComments(postId)
            }
            result.onSuccess { comments ->
                _commentListUIState.update {
                    it.copy(
                        isLoading = false,
                        comments = comments
                    )
                }
            }
                .onFailure { error ->
                    _commentListUIState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }
}