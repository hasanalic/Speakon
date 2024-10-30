package com.better.betterapp.feature_speaking_detail.presentation

import com.better.betterapp.feature_speaking_detail.domain.model.SpeakingDetail

data class SpeakingDetailState (
    val speakingDetail: SpeakingDetail? = null,
    val isSelectedTextHumanBased: Boolean = true,
    val isLoading: Boolean = false,
    val error: String = ""
)