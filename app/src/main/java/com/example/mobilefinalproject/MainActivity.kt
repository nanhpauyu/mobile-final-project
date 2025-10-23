package com.example.mobilefinalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.example.mobilefinalproject.features.auth.ui.screen.LoginScreen
import com.example.mobilefinalproject.features.comment.ui.commentlist.CommentListScreen
import com.example.mobilefinalproject.nav.AppNavGraph
import com.example.mobilefinalproject.ui.theme.MobileFinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileFinalProjectTheme {
//                AppNavGraph()
                LoginScreen(
                    onRegisterClick = { /*TODO*/ },
                    onLoginSuccess = { /*TODO*/ }
                )
            }
        }
    }
}