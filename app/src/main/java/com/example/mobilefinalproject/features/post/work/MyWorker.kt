package com.example.mobilefinalproject.features.post.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.google.gson.Gson

class MyWorker(
    val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    private val gson = Gson()
    override suspend fun doWork(): Result {

        return runCatching {
            val postAPIService = ApiProvider.getPostAPIService(appContext)

            val postListResponseDto = postAPIService.getPosts()
            val postListJson = gson.toJson(postListResponseDto)
            Log.d("hello", "Posts: $postListJson")
            val output = workDataOf(
                "postListResponseDto" to postListJson
            )
            Result.success(output)
        }.fold(
            onSuccess = { success ->
                success
            },
            onFailure = { error ->
                Result.retry()
            }
        )
    }
}