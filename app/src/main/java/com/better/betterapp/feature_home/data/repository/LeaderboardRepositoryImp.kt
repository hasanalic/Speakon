package com.better.betterapp.feature_home.data.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.User
import com.better.betterapp.feature_home.domain.repository.LeaderboardRepository

class LeaderboardRepositoryImp : LeaderboardRepository {
    override suspend fun getUsers(): Result<List<User>, DataError.Network> {
        TODO("Not yet implemented")
    }
}