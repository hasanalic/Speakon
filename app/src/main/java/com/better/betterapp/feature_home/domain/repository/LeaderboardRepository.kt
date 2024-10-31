package com.better.betterapp.feature_home.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.User

interface LeaderboardRepository {
    suspend fun getUsers(): Result<List<User>, DataError.Network>
}