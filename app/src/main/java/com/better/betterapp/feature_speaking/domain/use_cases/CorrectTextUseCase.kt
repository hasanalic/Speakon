package com.better.betterapp.feature_speaking.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_speaking.data.CorrectedTextResult
import com.better.betterapp.feature_speaking.domain.repository.SpeakingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CorrectTextUseCase @Inject constructor(
    private val speakingRepository: SpeakingRepository
) {
    operator fun invoke(speakingText: String, topic: String): Flow<Result<CorrectedTextResult, DataError.Network>> {
        return speakingRepository.correctText(speakingText, topic)
    }
}