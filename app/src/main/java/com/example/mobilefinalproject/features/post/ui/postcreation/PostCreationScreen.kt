package com.example.mobilefinalproject.features.post.ui.postcreation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.AppState
import com.example.mobilefinalproject.core.AppStateProvider
import com.example.mobilefinalproject.core.ai.data.repository.GenerativeModelRepositoryImpl
import com.example.mobilefinalproject.core.ai.model.GenerativeModelService
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.example.mobilefinalproject.features.post.data.repository.PostRepositoryImpl

@Composable
fun PostCreationScreen(modifier: Modifier = Modifier, onSuccess: () -> Unit,) {
    val context = LocalContext.current
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val appState: AppState = AppStateProvider.getAppState(context)
            val currentUser by appState.currentUser.collectAsStateWithLifecycle()

            val postCreationViewModel: PostCreationViewModel = viewModel {
                PostCreationViewModel(
                    generativeModelRepository = GenerativeModelRepositoryImpl(
                        model = GenerativeModelService.getModel()
                    ),
                    postRepository = PostRepositoryImpl(
                        postAPIService = ApiProvider.getPostAPIService(context)
                    ),
//                    currentUserRepository = CurrentUserRepositoryImpl(PreferencesDataSource(context))

                )
            }
            val postCreationUIState by postCreationViewModel.postCreationUIState.collectAsStateWithLifecycle()
            val aiPostCreationUIState by postCreationViewModel.aiPostCreationUIState.collectAsStateWithLifecycle()



            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = currentUser?.username!!,
                fontWeight = FontWeight.Bold,
                // Note: horizontalAlignment is for the Column/Parent. Use modifier.align(Alignment.Start)
                // to align the Text within the Column, and remove textAlign if alignment is applied.
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                // 2. Use the standard value/onValueChange API
                value = postCreationUIState.post,
                onValueChange = {
                    postCreationViewModel.onPostChange(it)
                },

                label = { Text("What is on your mind") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),

                // 3. Set minLines to increase the height naturally
                minLines = 8, // Set this to the desired number of minimum lines (e.g., 8 lines tall)
                singleLine = false // Ensure it can wrap to multiple lines
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    onClick = {
                        postCreationViewModel.onAIModify()
                    }
                ) {
                    Text(
                        text = "AI modify",
                    )
                }
                Button(
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    onClick = {
                        postCreationViewModel.onPostSubmit(username=currentUser?.username!!, userId =  currentUser?.id!!)
                    }
                ) {
                    Text(
                        text = "Post Original",
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            when {
                aiPostCreationUIState.isLoading == true -> LinearProgressIndicator()
            }
            Log.i("text", aiPostCreationUIState.post)
            Log.i("text", aiPostCreationUIState.errorMessage.toString())
            if (aiPostCreationUIState.is_clicked && !aiPostCreationUIState.isLoading && aiPostCreationUIState.errorMessage == null) {
                OutlinedTextField(
                    // 2. Use the standard value/onValueChange API
                    value = aiPostCreationUIState.post,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),

                    // 3. Set minLines to increase the height naturally
                    minLines = 8, // Set this to the desired number of minimum lines (e.g., 8 lines tall)
                    singleLine = false // Ensure it can wrap to multiple lines
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), // Row takes up full width
                    horizontalArrangement = Arrangement.End // Pushes all content to the right
                ) {
                    Button(
                        onClick = {
                            postCreationViewModel.onAIModifyPostSubmit(currentUser?.username!!, currentUser?.id!!)
                        },
                        // Remove .fillMaxWidth() from the button
                        // Optional: Add padding here if needed
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(width = 150.dp, height = 50.dp)
                    ) {
                        Text(text = "AI modify Post")
                    }
                }
            }
            if (aiPostCreationUIState.errorMessage != null) {
                Text(
                    text = aiPostCreationUIState.errorMessage.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    textAlign = TextAlign.Start
                )
            }
            LaunchedEffect(
                postCreationUIState.onSuccessUpload
            ) {
                if(postCreationUIState.onSuccessUpload == true){
                    onSuccess()
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PostScreenPreview(modifier: Modifier = Modifier) {
//    PostScreen()
}