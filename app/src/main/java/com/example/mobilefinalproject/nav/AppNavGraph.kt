package com.example.mobilefinalproject.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.mobilefinalproject.features.auth.ui.LoginScreen
import com.example.mobilefinalproject.features.auth.ui.RegisterScreen
import com.example.mobilefinalproject.features.comment.ui.commentlist.CommentListScreen
import com.example.mobilefinalproject.features.post.ui.postcreation.PostScreen
import com.example.mobilefinalproject.features.post.ui.postlist.PostListScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack<AppNavKey>(Login)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),

        // android DSL
        entryProvider = entryProvider {
            entry<Register> {
                RegisterScreen(
                    modifier = modifier,
                    onLoginClick = {
                        backStack.add(Login)
                    },
                )
            }
            entry<Login> {
                LoginScreen(
                    modifier = modifier,
                )
            }
            entry<PostList> {
                PostListScreen(
                    modifier = modifier,
                    onCommentClick = { commentList ->
                        backStack.add(commentList)
                    },
                    onCreateClick = {
                        backStack.add(AddPost)
                    },
                    onLogoutClick = {

                    },
                    onProfileClick = {

                    }
                )
            }
            entry<AddPost> {
                PostScreen(
                    modifier = modifier,
                    onSuccess = {
                        backStack.removeLastOrNull()
                    }
                )
            }
            entry<CommentList> {
                CommentListScreen(
                    modifier = modifier,
                    postId = it.postId,
                    post = it.post,
                    username = it.username
                )
            }
        },
    )
}