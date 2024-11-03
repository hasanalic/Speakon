package com.better.betterapp.feature_home.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.use_cases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _stateMain = mutableStateOf(MainState())
    val stateMain: State<MainState> = _stateMain

    init {
        getDailyTopic()
        getSpeakingPosts()
    }

    private var getDailyTopicJob: Job? = null

    private fun getDailyTopic() {
        val topicId = "aBaWq3T5VvixRucYms9k"
        getDailyTopicJob?.cancel()
        getDailyTopicJob = homeUseCases.getDailyTopicUseCase(topicId).onEach { result ->
            when(result) {
                is Result.Success -> {
                    _stateMain.value = _stateMain.value.copy(topic = result.data)
                }

                is Result.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    private var getSpeakingPostsJob: Job? = null

    private fun getSpeakingPosts() {
        _stateMain.value = _stateMain.value.copy(isLoading = true)

        getSpeakingPostsJob?.cancel()
        getSpeakingPostsJob = homeUseCases.getSpeakingPostsUseCase().onEach { result ->
            when(result) {
                is Result.Success -> {
                    _stateMain.value = _stateMain.value.copy(isLoading = false, speakingPosts = result.data)
                }

                is Result.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}