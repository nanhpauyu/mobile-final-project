package com.example.mobilefinalproject.feature.auth.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.network.ApiProvider
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
            Text("Register")
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
            Row (
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    modifier = Modifier.clickable {
                        // redirect to login screen
                    },
                    text = "Login",
                )
                Button(onClick = authViewModel::register) {
                    Text("Register")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}