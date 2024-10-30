package com.better.betterapp.feature_speaking_detail.presentation

import com.better.betterapp.feature_speaking_detail.domain.model.SpeakingDetail

data class SpeakingDetailState (
    val speakingDetail: SpeakingDetail? = null,
    val selectedText: String = "HUMAN",
    val isLoading: Boolean = false,
    val error: String = ""
)