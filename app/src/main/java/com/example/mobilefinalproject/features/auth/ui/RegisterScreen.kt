package com.example.mobilefinalproject.features.auth.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.example.mobilefinalproject.feature.auth.data.repository.AuthRepositoryImpl

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    val authViewModel: AuthViewModel = viewModel {
        AuthViewModel(AuthRepositoryImpl(ApiProvider.authService))
    }
    val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column (
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text="Register",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,

            )
            OutlinedTextField(
                value = authUiState.username,
                onValueChange = authViewModel::onUserNameChange,
                label = { Text("Username") },
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
            Button(onClick = authViewModel::register) {
                Text("Register")
            }
            Text(
                modifier = Modifier
                    .clickable {
                        // redirect to login screen
                    },
                text = "Login",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}