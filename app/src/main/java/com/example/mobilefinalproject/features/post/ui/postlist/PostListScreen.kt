package com.example.mobilefinalproject.features.post.ui.postlist


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilefinalproject.core.AppState
import com.example.mobilefinalproject.core.AppStateProvider
import com.example.mobilefinalproject.core.AppStateProvider.appState
import com.example.mobilefinalproject.core.data.network.ApiProvider
import com.example.mobilefinalproject.features.post.data.remote.api.PostAPIService
import com.example.mobilefinalproject.features.post.data.repository.PostRepositoryImpl
import com.example.mobilefinalproject.nav.AddPost
import com.example.mobilefinalproject.nav.CommentList
import kotlinx.coroutines.runBlocking

@Composable
fun PostListScreen(
    modifier: Modifier = Modifier,
    onCommentClick: (CommentList) -> Unit,
    onCreateClick: (AddPost) -> Unit,
    // need to edit
    onProfileClick:() ->Unit,
    onLogoutClick:() ->Unit
) {
    val context = LocalContext.current
    val postAPIService: PostAPIService = remember {
        ApiProvider.getPostAPIService(context)
    }
    val postListViewModel: PostListViewModel = viewModel {
        PostListViewModel(PostRepositoryImpl(postAPIService))
    }
    val postListUIState by postListViewModel.postListUIState.collectAsStateWithLifecycle()

    val posts = postListUIState.posts?.data
    val appState: AppState = AppStateProvider.getAppState(context)

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    onClick = {
                        runBlocking {
                            runCatching {
                                appState.clearCurrentUser()
                            }
                        }.onSuccess {
                            onLogoutClick()
                        }
                    }
                ) {
                    Text(
                        text = "Log out",
                    )
                }
                TextButton(
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    onClick = {
                        onProfileClick()
                    }
                ) {
                    Text(
                        text = "Profile",
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp) // Increased height
                    .border(
                        width = 1.dp,
                        color = Color.Black, // You can change the color
                        shape = RoundedCornerShape(4.dp) // Optional: rounded corners
                    )
                    .clickable {
                        onCreateClick(AddPost)
                    },
                contentAlignment = Alignment.CenterStart // Changed to CenterStart for left alignment
            ) {
                Text(
                    text = "What is on your mind?",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp) // Make text container full width
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            LazyColumn {
                items(posts ?: emptyList()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                onCommentClick(
                                    CommentList(
                                        postId = it.id,
                                        username = it.username,
                                        post = it.text
                                    )
                                )
                            },
                        contentAlignment = Alignment.TopStart
                    ) {

                        Column {
                            Row(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "${it.username} :"
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(
                                    text = it.text
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth(Alignment.End) // This pushes the box to the right
                                        .border(
                                            width = 1.dp,
                                            color = Color.Gray,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(8.dp)
                                ) {
                                    Text(text = "See Comments")
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
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
fun PostListScreenPreview(modifier: Modifier = Modifier) {
//    PostListScreen(modifier, null)
}