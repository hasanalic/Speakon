package com.better.betterapp.feature_speaking.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost

interface SpeakingRepository {
    suspend fun correctText(text: String): Result<String, DataError.Network>

    suspend fun publishSpeaking(speakingPost: SpeakingPost, aiCorrectedText: String): Result<Unit, DataError.Network>
}