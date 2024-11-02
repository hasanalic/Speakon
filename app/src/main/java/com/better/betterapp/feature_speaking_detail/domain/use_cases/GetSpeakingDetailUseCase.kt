package com.better.betterapp.feature_speaking_detail.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_speaking_detail.domain.model.SpeakingDetail
import com.better.betterapp.feature_speaking_detail.domain.repository.SpeakingDetailRepository
import kotlinx.coroutines.flow.Flow

class GetSpeakingDetailUseCase(
    private val speakingDetailRepository: SpeakingDetailRepository
) {
    operator fun invoke(postId: String): Flow<Result<SpeakingDetail, DataError.Network>> {
        return speakingDetailRepository.getSpeakingDetail(postId)
    }
}