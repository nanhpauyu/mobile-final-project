package com.example.mobilefinalproject.features.post.ui.postlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mobilefinalproject.features.post.data.remote.dto.response.PostListResponseDto
import com.example.mobilefinalproject.features.post.repository.PostRepository
import com.example.mobilefinalproject.features.post.work.MyWorker
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class PostListViewModel(
    private val postRepository: PostRepository,
    private val workManager: WorkManager
) : ViewModel() {
    private val _postListUIState = MutableStateFlow(PostListUIState())
    val postListUIState = _postListUIState.asStateFlow()
    private val gson = Gson()

    init {
        getPosts()
        viewModelScope.launch {
            workManager.getWorkInfosForUniqueWorkFlow("SyncPosts")
                .collect { workInfos ->
                    val workInfo = workInfos.firstOrNull()
                    val postListJson = workInfo?.outputData?.getString("postListJson")
                    val postListDto = if (postListJson != null) {
                        // 💡 Deserialize the JSON string back into the DTO object
                        gson.fromJson(postListJson, PostListResponseDto::class.java)
                    } else {
                        null
                    }
                    _postListUIState.update {
                        it.copy(
                            posts = postListDto // Update with the actual DTO object
                        )
                    }
                }
        }

    }


    fun startWork(){
        val periodicWorkRequest = PeriodicWorkRequestBuilder<MyWorker>(
            repeatInterval = 3,
            repeatIntervalTimeUnit = TimeUnit.SECONDS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            "SyncPosts", // Use the unique name you used to collect results
            ExistingPeriodicWorkPolicy.KEEP, // Policy on how to handle existing work
            periodicWorkRequest
        )
        //        workManager.enqueueUniquePeriodicWork(
//            UNIQUE_WORKNAME,
//            ExistingPeriodicWorkPolicy.KEEP,
//            periodicWorkRequest
//        )
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
                Log.d("hello", "Posts: $posts")
                _postListUIState.update {
                    it.copy(
                        isLoading = false,
                        posts = posts
                    )
                }
            }
                .onFailure { error ->
                    Log.d("hello", "Posts: $error")
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