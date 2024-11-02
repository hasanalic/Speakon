package com.better.betterapp.feature_home.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetSpeakingPostsUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<Result<List<SpeakingPost>, DataError.Network>> {
        return homeRepository.getSpeakingPosts()
    }
}