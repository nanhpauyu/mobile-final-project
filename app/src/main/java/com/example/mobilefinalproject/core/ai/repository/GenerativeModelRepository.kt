package com.example.mobilefinalproject.core.ai.repository

interface GenerativeModelRepository {
    suspend fun generateText(prompt: String): Result<String?>

}