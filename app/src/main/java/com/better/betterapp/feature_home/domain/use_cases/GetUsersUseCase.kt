package com.better.betterapp.feature_home.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.User
import com.better.betterapp.feature_home.domain.repository.LeaderboardRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val leaderboardRepository: LeaderboardRepository
) {
    operator fun invoke(): Flow<Result<List<User>, DataError.Network>> {
        return leaderboardRepository.getUsers()
    }
}