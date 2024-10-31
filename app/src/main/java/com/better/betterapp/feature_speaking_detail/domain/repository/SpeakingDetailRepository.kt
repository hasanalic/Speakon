package com.better.betterapp.feature_speaking_detail.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_speaking_detail.domain.model.SpeakingDetail

interface SpeakingDetailRepository {
    suspend fun getSpeakingDetail(postId: Int): Result<SpeakingDetail, DataError.Network>
}