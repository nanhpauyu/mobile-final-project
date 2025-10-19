package com.example.mobilefinalproject.core.ai.data.repository

import com.example.mobilefinalproject.core.ai.repository.GenerativeModelRepository
import com.google.firebase.ai.GenerativeModel

class GenerativeModelRepositoryImpl(
    private val model: GenerativeModel
) : GenerativeModelRepository {
    override suspend fun generateText(prompt: String): Result<String?> {
        return runCatching {
            model.generateContent(
                "Modify the post to friendly facebook post. " +
                        "Give me result with starting Result-. " +
                        "Thi is ths post:${prompt}"
            )
                .text
        }
    }
}