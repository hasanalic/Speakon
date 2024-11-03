package com.better.betterapp.feature_home.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.Profile
import com.better.betterapp.feature_home.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(userId: String): Flow<Result<Profile, DataError.Network>> {
        return profileRepository.getProfile(userId)
    }
}