package com.example.mobilefinalproject.ui.test

import com.example.mobilefinalproject.data.FakeProfileRepository
import com.example.mobilefinalproject.features.profile.ui.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class ProfileViewModelTest {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileViewModel(FakeProfileRepository())
    }

    @Test
    fun testInitialState() {
        val alphabetState = viewModel.profileUiState.value
        assert(!alphabetState.isLoading)
        assert(alphabetState.errorMessage == null)
    }

    @Test
    fun testGetProfileById() {
        runTest {
            viewModel.getProfileById("1")
            advanceUntilIdle()
        }

        val profileState = viewModel.profileUiState.value
        assert(profileState.username == "foo")
        assert(profileState.bio == "my bio")
        assert(profileState.email == "f@mail.com")
        assert(profileState.id == "1")
    }

    @Test
    fun testEditProfile() {
        viewModel.onUsernameChange("new username")
        viewModel.onBioChange("new bio")
        runTest {
            viewModel.editProfile()
            advanceUntilIdle()
        }

        val profileState = viewModel.profileUiState.value
        assert(profileState.username == "new username")
        assert(profileState.bio == "new bio")
    }
}