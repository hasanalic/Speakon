package com.better.betterapp.feature_home.data.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.Profile
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_home.domain.repository.ProfileRepository

class ProfileRepositoryImp : ProfileRepository{
    override suspend fun getProfile(userId: String): Result<Profile, DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun getUsersPosts(userId: String): Result<List<SpeakingPost>, DataError.Network> {
        TODO("Not yet implemented")
    }
}