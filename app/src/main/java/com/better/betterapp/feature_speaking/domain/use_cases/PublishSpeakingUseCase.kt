package com.better.betterapp.feature_speaking.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_speaking.domain.repository.SpeakingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PublishSpeakingUseCase @Inject constructor(
    private val speakingRepository: SpeakingRepository
) {
    operator fun invoke(speakingPost: SpeakingPost, aiCorrectedText: String): Flow<Result<Unit, DataError.Network>> {
        return speakingRepository.publishSpeaking(speakingPost, aiCorrectedText)
    }
}