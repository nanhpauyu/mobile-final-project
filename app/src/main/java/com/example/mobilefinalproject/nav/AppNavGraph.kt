package com.example.mobilefinalproject.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.mobilefinalproject.features.auth.ui.screen.LoginScreen
import com.example.mobilefinalproject.features.auth.ui.screen.RegisterScreen
import com.example.mobilefinalproject.features.comment.ui.commentlist.CommentListScreen
import com.example.mobilefinalproject.features.post.ui.postlist.PostListScreen
import com.example.mobilefinalproject.features.profile.ui.screen.ProfileScreen
import com.example.mobilefinalproject.features.post.ui.postcreation.PostCreationScreen

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
                        backStack.clear()
                        backStack.add(Login)
                    },
                    onRegisterSuccess = {
                        backStack.clear()
                        backStack.add(PostList)
                    }
                )
            }
            entry<Login> {
                LoginScreen(
                    modifier = modifier,
                    onRegisterClick = {
                        backStack.clear()
                        backStack.add(Register)
                    },
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(PostList)
                    }
                )
            }
            entry<Profile> {
                ProfileScreen(
                    modifier = modifier,
                    userId = it.id,
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
                        backStack.clear()
                        backStack.add(Login)
                    },
                    onProfileClick = {

                    }
                )
            }
            entry<AddPost> {
                PostCreationScreen(
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