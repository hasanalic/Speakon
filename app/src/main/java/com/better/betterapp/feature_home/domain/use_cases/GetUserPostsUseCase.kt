package com.better.betterapp.feature_home.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_home.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetUserPostsUseCase(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(userId: String): Flow<Result<List<SpeakingPost>, DataError.Network>> {
        return profileRepository.getUsersPosts(userId)
    }
}