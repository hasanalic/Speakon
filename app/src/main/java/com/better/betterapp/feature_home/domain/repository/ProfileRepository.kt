package com.better.betterapp.feature_home.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.Profile
import com.better.betterapp.feature_home.domain.model.SpeakingPost

interface ProfileRepository {
    suspend fun getProfile(userId: String): Result<Profile, DataError.Network>

    suspend fun getUsersPosts(userId: String): Result<List<SpeakingPost>, DataError.Network>
}