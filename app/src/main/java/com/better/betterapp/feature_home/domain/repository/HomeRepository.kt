package com.better.betterapp.feature_home.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost

interface HomeRepository {
    suspend fun getSpeakingPosts(): Result<List<SpeakingPost>, DataError.Network>

    suspend fun getDailyTopic(): Result<String, DataError.Network>
}