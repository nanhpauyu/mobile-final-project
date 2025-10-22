package com.example.mobilefinalproject.features.auth.ui.screen

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.AppState
import com.example.mobilefinalproject.core.AppStateProvider
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.example.mobilefinalproject.features.auth.data.repository.AuthRepositoryImpl
import com.example.mobilefinalproject.features.auth.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    ) {
    val authViewModel: AuthViewModel = viewModel {
        AuthViewModel(AuthRepositoryImpl(ApiProvider.authService))
    }
    val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val appState: AppState = AppStateProvider.getAppState(context)
    val currentUser by appState.currentUser.collectAsStateWithLifecycle()

    LaunchedEffect(authUiState.userDetail) {
        if (authUiState.userDetail != null) {
            appState.saveCurrentUser(authUiState.userDetail!!)
        }
    }

    LaunchedEffect(appState.currentUser) {
        if (currentUser != null) {
            onLoginSuccess()
        }
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
                visualTransformation = PasswordVisualTransformation(),
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
                        onRegisterClick()
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
//    LoginScreen()
}
