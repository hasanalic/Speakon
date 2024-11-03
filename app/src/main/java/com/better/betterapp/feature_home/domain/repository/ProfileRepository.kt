package com.better.betterapp.feature_home.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.Profile
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(userId: String): Flow<Result<Profile, DataError.Network>>

    fun getUsersPosts(userId: String): Flow<Result<List<SpeakingPost>, DataError.Network>>
}