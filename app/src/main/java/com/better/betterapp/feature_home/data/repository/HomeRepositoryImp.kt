package com.better.betterapp.feature_home.data.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_home.domain.repository.HomeRepository

class HomeRepositoryImp : HomeRepository {
    override suspend fun getSpeakingPosts(): Result<List<SpeakingPost>, DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyTopic(): Result<String, DataError.Network> {
        TODO("Not yet implemented")
    }
}