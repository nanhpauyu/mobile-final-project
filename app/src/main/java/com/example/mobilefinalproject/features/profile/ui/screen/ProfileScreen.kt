package com.example.mobilefinalproject.features.profile.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.AppState
import com.example.mobilefinalproject.core.AppStateProvider
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.example.mobilefinalproject.features.profile.data.repository.ProfileRepositoryImpl
import com.example.mobilefinalproject.features.profile.ui.state.EditProfileUiState
import com.example.mobilefinalproject.features.profile.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, userId: String? = null) {
    val context = LocalContext.current
    val profileViewModel = viewModel {
        ProfileViewModel(ProfileRepositoryImpl(ApiProvider.getProfileService(context)))
    }
    val profileUiState by profileViewModel.profileUiState.collectAsStateWithLifecycle()
    val editProfileUiState by profileViewModel.editProfileUiState.collectAsStateWithLifecycle()

    val appState: AppState = AppStateProvider.getAppState(context)
    val currentUser by appState.currentUser.collectAsStateWithLifecycle()

    val editDialog = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(userId, currentUser?.id) {
        profileViewModel.getProfileById(userId ?: currentUser?.id!!)
    }

    Scaffold (
        floatingActionButton = {
            if (currentUser != null && currentUser!!.id == profileUiState.id) {
                FloatingActionButton(
                    onClick = {
                        profileViewModel.setEditProfile()
                        editDialog.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit"
                    )
                }
            }
        }
    ){ innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth(),
        ) {
            Card(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Profile Detail",
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                    HorizontalDivider()
                    Text(text = "Username: ${profileUiState.username}")
                    Text(text = "Email: ${profileUiState.email}")
                    Text(text = "Bio: ${profileUiState.bio}")
                    when {
                        editProfileUiState.isLoading -> {
                            LinearProgressIndicator()
                        }
                        editProfileUiState.errorMessage != null -> {
                            Text(text = "Error: ${editProfileUiState.errorMessage}")
                        }
                    }
                }
            }
        }
    }

    if (currentUser != null && currentUser!!.id == profileUiState.id && editDialog.value) {
        EditProfileDialog(
            createItemUiState = editProfileUiState,
            createItemViewModel = profileViewModel,
            onDismissRequest = {
                editDialog.value = false
            },
            onConfirm = {
                profileViewModel.editProfile()
                editDialog.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    createItemUiState: EditProfileUiState,
    createItemViewModel: ProfileViewModel,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = createItemUiState.username,
                    onValueChange = createItemViewModel::onUsernameChange,
                    label = { Text("Username") }
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = createItemUiState.bio,
                    onValueChange = createItemViewModel::onBioChange,
                    label = { Text("Bio") }
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(
                    onClick = onConfirm,
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}