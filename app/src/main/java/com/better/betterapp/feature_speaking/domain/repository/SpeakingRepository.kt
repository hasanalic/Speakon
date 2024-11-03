package com.better.betterapp.feature_speaking.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_speaking.data.CorrectedTextResult
import kotlinx.coroutines.flow.Flow

interface SpeakingRepository {
    fun correctText(speakingText: String, topic: String): Flow<Result<CorrectedTextResult, DataError.Network>>

    fun publishSpeaking(speakingPost: SpeakingPost, aiCorrectedText: String): Flow<Result<Unit, DataError.Network>>
}