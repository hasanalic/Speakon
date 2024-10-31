package com.better.betterapp.feature_speaking_detail.data.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_speaking_detail.domain.model.SpeakingDetail
import com.better.betterapp.feature_speaking_detail.domain.repository.SpeakingDetailRepository

class SpeakingDetailRepositoryImp : SpeakingDetailRepository {
    override suspend fun getSpeakingDetail(postId: Int): Result<SpeakingDetail, DataError.Network> {
        TODO("Not yet implemented")
    }
}