package com.example.mobilefinalproject.features.post.ui.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.features.post.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostListViewModel(
    private val postRepository: PostRepository
) : ViewModel() {
    private val _postListUIState = MutableStateFlow(PostListUIState())
    val postListUIState = _postListUIState.asStateFlow()

    init {
        getPosts()
    }




    fun getPosts() {
        viewModelScope.launch {
            _postListUIState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val result = withContext(Dispatchers.IO) {
                postRepository.getPosts()
            }
            result.onSuccess { posts ->
                _postListUIState.update {
                    it.copy(
                        isLoading = false,
                        posts = posts
                    )
                }
            }
                .onFailure { error ->
                    _postListUIState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }

        }
    }


}