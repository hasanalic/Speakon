package com.better.betterapp.feature_home.presentation.leaderboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

class LeaderboardViewModel @Inject constructor(

) : ViewModel() {

    private val _stateLeaderboard = mutableStateOf(LeaderboardState())
    val stateLeaderboard: State<LeaderboardState> = _stateLeaderboard

    init {
        getLeaderboardList()
    }

    private var getLeaderboardListJob: Job? = null

    private fun getLeaderboardList() {

    }
}