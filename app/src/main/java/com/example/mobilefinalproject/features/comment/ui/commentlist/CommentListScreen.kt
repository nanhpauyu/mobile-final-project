package com.example.mobilefinalproject.features.comment.ui.commentlist

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.AppState
import com.example.mobilefinalproject.core.AppStateProvider
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.example.mobilefinalproject.features.comment.data.remote.api.CommentAPIService
import com.example.mobilefinalproject.features.comment.data.repository.CommentRepositoryImpl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentListScreen(
    modifier: Modifier = Modifier,
    postId: String,
    userId: String,
    post: String,
    username: String,
    onViewUserProfileClicked: (userId: String) -> Unit
) {
    val context = LocalContext.current
    val appState: AppState = AppStateProvider.getAppState(context)
    val currentUser by appState.currentUser.collectAsStateWithLifecycle()
    val commentAPIService: CommentAPIService = remember {
        ApiProvider.getCommentAPIService(context)
    }
    val commentListViewModel: CommentListViewModel = viewModel {
        CommentListViewModel(
            commentRepository = CommentRepositoryImpl(
                commentAPIService = commentAPIService
            ),
            postId = postId,
            username = username,
            post = post
        )
    }
    val commentListUIState by commentListViewModel.commentListUIState.collectAsStateWithLifecycle()
    val comments = commentListUIState.comments?.data
    val commentCreateViewModel: CommentCreateViewModel = viewModel {
        CommentCreateViewModel(
            CommentRepositoryImpl(commentAPIService),
            postId
        )
    }
    val commentCreateUIState by commentCreateViewModel.commentCreateUIState.collectAsStateWithLifecycle()
    var openDialog = rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog.value = true

                },
                containerColor = Color.Red,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        onViewUserProfileClicked(userId)
                    },
                contentAlignment = Alignment.TopStart
            ) {

                Column(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        top = 5.dp,
                        bottom = 10.dp,
                        end = 10.dp
                    )
                ) {
                    Text(
                        text = username
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = post,
                        fontSize = 20.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))

            LazyColumn {
                items(items = comments ?: emptyList()) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(8.dp)
                            .clickable {
                                onViewUserProfileClicked(it.userId)
                            },
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = it.username ?: "Username",
                                    modifier = Modifier
                                        .weight(1f, fill = true)
                                        .padding(end = 3.dp)// Fills available space
                                )
                                Text(
                                    text = it.text,
                                    modifier = Modifier
                                        .weight(3f, fill = true)
                                        .padding(start = 3.dp)// Only takes needed space
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = commentCreateUIState.text,
                        onValueChange = {
                            commentCreateViewModel.updateComment(it)
                        },
                        label = { Text("Comment") }
                    )
                    Spacer(modifier = Modifier.padding(7.dp))
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            commentCreateViewModel.createComment(
                                username = currentUser?.username!!,
                                userId = currentUser?.id!!
                            )
                            commentListViewModel.getComments(postId = postId)
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Comment"
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(
        commentCreateUIState.isSuccess
    ) {
        if (commentCreateUIState.isSuccess == true) {
            commentCreateViewModel.resetCreateCommentUIState()
        }
        commentListViewModel.getComments(postId)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CommentListScreenPreview(modifier: Modifier = Modifier) {
//    CommentListScreen()
}