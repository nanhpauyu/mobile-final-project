package com.example.mobilefinalproject.feature.auth.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.example.mobilefinalproject.core.model.CurrentUser
import com.example.mobilefinalproject.core.viewmodel.AppViewModelProvider
import com.example.mobilefinalproject.core.viewmodel.CurrentUserViewModel
import com.example.mobilefinalproject.feature.auth.data.repository.AuthRepositoryImpl

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val authViewModel: AuthViewModel = viewModel {
        AuthViewModel(AuthRepositoryImpl(ApiProvider.authService))
    }
    val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val currentUserViewModel: CurrentUserViewModel = AppViewModelProvider.getCurrentUserViewModel(context)

    LaunchedEffect(authUiState.isLogin) {
        // TODO
//        currentUserViewModel.saveCurrentUser(
//            CurrentUser(
//                id = authUiState.id,
//                username = authUiState.username,
//                email = authUiState.email,
//                bio = authUiState.bio,
//                accessToken = authUiState.accessToken
//            )
//        )
    }

    Scaffold { innerPadding ->
        Column (
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text="Login",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            OutlinedTextField(
                value = authUiState.email,
                onValueChange = authViewModel::onEmailChange,
                label = { Text("Email") },
            )
            OutlinedTextField(
                value = authUiState.password,
                onValueChange = authViewModel::onPasswordChange,
                label = { Text("Password") },
            )
            Button(
                onClick = authViewModel::login
            ) {
                Text("Login")
            }
            Text(
                modifier = Modifier
                    .clickable {
                        // redirect to register screen
                    },
                text = "Register",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
