package com.example.mobilefinalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mobilefinalproject.features.comment.ui.commentlist.CommentListScreen
import com.example.mobilefinalproject.ui.theme.MobileFinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileFinalProjectTheme {
//                PostScreen()
//                PostListScreen()
                CommentListScreen(
                    postId ="38ff4ac6-22f8-4c8d-93b4-f5865cbf2ff7",
                    post="Logo Is Here!",
                    username = "null"
                )
            }
        }
    }
}