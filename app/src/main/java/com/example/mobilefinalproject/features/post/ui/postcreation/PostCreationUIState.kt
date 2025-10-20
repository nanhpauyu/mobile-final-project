package com.example.mobilefinalproject.features.post.ui.postcreation

data class PostCreationUIState(
    val isLoading: Boolean =false,
    val errorMessage:String? = null,
    val post:String ="",
    val onSuccessUpload: Boolean =false,
){
    companion object{
        val Empty = PostCreationUIState()
    }
}
