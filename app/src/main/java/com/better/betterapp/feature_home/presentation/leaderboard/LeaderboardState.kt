package com.better.betterapp.feature_home.presentation.leaderboard

import com.better.betterapp.feature_home.domain.model.User

data class LeaderboardState (
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)