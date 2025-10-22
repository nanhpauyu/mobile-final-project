package com.example.mobilefinalproject.features.post.ui.postcreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.core.ai.repository.GenerativeModelRepository
import com.example.mobilefinalproject.core.domain.CurrentUserRepository
import com.example.mobilefinalproject.features.post.data.remote.dto.request.PostRequestDto
import com.example.mobilefinalproject.features.post.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostCreationViewModel(
    private val generativeModelRepository: GenerativeModelRepository,
    private val postRepository: PostRepository,
//    private val currentUserRepository: CurrentUserRepository
) : ViewModel() {
    private val _postCreationUIState = MutableStateFlow(PostCreationUIState())
    val postCreationUIState = _postCreationUIState.asStateFlow()
    private val _aiPostCreationUIState = MutableStateFlow(AIPostCreationUIState())
    val aiPostCreationUIState = _aiPostCreationUIState.asStateFlow()

//    init {
//        viewModelScope.launch {
//            currentUserRepository.getUserCredentials().collect { user ->
//                if (user != null) {
//                    _postCreationUIState.update{
//                        it.copy(
//                            username = user.username,
//                            userid = user.id.toString()
//                        )
//                    }
//                }
//            }
//        }
//    }

    fun onPostChange(post: String) {
        _postCreationUIState.update {
            it.copy(
                post = post
            )
        }
    }

    fun onAIPostChange(post: String) {
        _aiPostCreationUIState.update {
            it.copy(
                post = post
            )
        }
    }

    fun onPostSubmit() {
        viewModelScope.launch {
            _postCreationUIState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )

            }
            val result = withContext(Dispatchers.IO) {
                postRepository.createPost(
                    PostRequestDto(
                        _postCreationUIState.value.post,
                        _postCreationUIState.value.userid,
                        _postCreationUIState.value.username)
                )
            }
            result.onSuccess {
                _postCreationUIState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        onSuccessUpload =true
                    )

                }
                _aiPostCreationUIState.update { AIPostCreationUIState.Empty }
            }
                .onFailure { error ->
                    _postCreationUIState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message,

                        )
                    }
                }
        }
    }

    fun onAIModifyPostSubmit() {
        _postCreationUIState.update {
            it.copy(
                post = _aiPostCreationUIState.value.post
            )
        }
        onPostSubmit()
    }

    fun onAIModify() {
        viewModelScope.launch {
            _aiPostCreationUIState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    is_clicked = true
                )
            }
            val result = withContext(Dispatchers.IO) {
                generativeModelRepository.generateText(_postCreationUIState.value.post)
            }
            result.onFailure { error: Throwable ->
                _aiPostCreationUIState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
            }.onSuccess { generatedText ->
                val result = generatedText!!.removePrefix("Result-").trim()
                _aiPostCreationUIState.update {
                    it.copy(
                        isLoading = false,
                        post = result,
                        errorMessage = null,
                    )
                }
            }

        }
    }

}