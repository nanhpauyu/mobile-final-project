package com.example.mobilefinalproject.features.post.ui.postcreation

data class AIPostCreationUIState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val post: String = "",
    val is_clicked: Boolean = false
){
    companion object{
        val Empty = AIPostCreationUIState()
    }
}
