package com.better.betterapp.feature_home.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.use_cases.HomeUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _stateProfile = mutableStateOf(ProfileState())
    val stateProfile: State<ProfileState> = _stateProfile

    init {
        getProfileAndSpeakingPosts()
    }

    private var getProfileJob: Job? = null
    private var getUsersPosts: Job? = null

    private fun getProfileAndSpeakingPosts() {
        getProfileJob?.cancel()
        getUsersPosts?.cancel()

        _stateProfile.value = _stateProfile.value.copy(isLoading = true)

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        getProfileJob = homeUseCases.getProfileUseCase(userId).onEach { result ->
            when(result) {
                is Result.Success -> {
                    _stateProfile.value = _stateProfile.value.copy(profile = result.data, isLoading = false)
                }

                is Result.Error -> {

                }
            }
        }.launchIn(viewModelScope)

        getUsersPosts = homeUseCases.getUsersPostsUseCase(userId).onEach { result ->
            when(result) {
                is Result.Success -> {
                    _stateProfile.value = _stateProfile.value.copy(usersSpeakingPosts = result.data, isLoading = false)
                }

                is Result.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}