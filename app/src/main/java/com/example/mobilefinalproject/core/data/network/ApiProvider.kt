package com.example.mobilefinalproject.core.data.network

import android.content.Context
import com.example.mobilefinalproject.features.auth.data.api.AuthService
import com.example.mobilefinalproject.features.comment.data.remote.api.CommentAPIService
import com.example.mobilefinalproject.features.post.data.remote.api.PostAPIService
import com.example.mobilefinalproject.features.profile.data.api.ProfileService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private var retrofit: Retrofit? = null
    private fun getRetrofit(context: Context): Retrofit {
        return retrofit ?: synchronized(this) {
            if (retrofit == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(context))
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl("http://localhost:8080/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            retrofit!!
        }
    }

    private var authService: AuthService? = null
    fun getAuthService(context: Context): AuthService {
        return authService ?: synchronized(this) {
           if (authService == null) {
               authService = getRetrofit(context).create(AuthService::class.java)
           }
           authService!!
        }
    }

    private var profileService: ProfileService? = null
    fun getProfileService(context: Context): ProfileService {
        return profileService ?: synchronized(this) {
            if (profileService == null) {
                profileService = getRetrofit(context).create(ProfileService::class.java)
            }
            profileService!!
        }
    }

    private var postAPIService: PostAPIService? = null
    fun getPostAPIService(context: Context): PostAPIService {
        return postAPIService ?: synchronized(this) {
            if (postAPIService == null) {
                postAPIService = getRetrofit(context).create(PostAPIService::class.java)
            }
            postAPIService!!
        }
    }

    private var commentAPIService: CommentAPIService? = null
    fun getCommentAPIService(context: Context): CommentAPIService {
        return commentAPIService ?: synchronized(this) {
            if (commentAPIService == null) {
                commentAPIService = getRetrofit(context).create(CommentAPIService::class.java)
            }
            commentAPIService!!
        }
    }
}
