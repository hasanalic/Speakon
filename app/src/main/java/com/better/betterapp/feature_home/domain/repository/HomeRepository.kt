package com.better.betterapp.feature_home.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getSpeakingPosts(): Flow<Result<List<SpeakingPost>, DataError.Network>>

    fun getDailyTopic(): Flow<Result<String, DataError.Network>>
}