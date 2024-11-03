package com.better.betterapp.feature_home.presentation.leaderboard

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
class LeaderboardViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _stateLeaderboard = mutableStateOf(LeaderboardState())
    val stateLeaderboard: State<LeaderboardState> = _stateLeaderboard

    init {
        getLeaderboardList()
    }

    private var getLeaderboardListJob: Job? = null

    private fun getLeaderboardList() {
        getLeaderboardListJob?.cancel()

        _stateLeaderboard.value = _stateLeaderboard.value.copy(isLoading = false)

        getLeaderboardListJob = homeUseCases.getUsersUseCase().onEach { result ->
            when(result) {
                is Result.Success -> {
                    _stateLeaderboard.value = _stateLeaderboard.value.copy(
                        isLoading = false,
                        users = result.data
                    )
                }

                is Result.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}