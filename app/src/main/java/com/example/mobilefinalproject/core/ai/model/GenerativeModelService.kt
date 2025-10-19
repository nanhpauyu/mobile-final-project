package com.example.mobilefinalproject.core.ai.model

import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

object GenerativeModelService {
    @Volatile
    private var model: GenerativeModel? = null

    fun getModel(): GenerativeModel {
        return model ?: synchronized(this){
            val model = Firebase.ai(backend = GenerativeBackend.Companion.googleAI())
                .generativeModel("gemini-2.5-flash")
            this.model = model
            model
        }
    }
}