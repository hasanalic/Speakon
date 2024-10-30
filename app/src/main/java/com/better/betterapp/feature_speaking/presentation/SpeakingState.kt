package com.better.betterapp.feature_speaking.presentation

data class SpeakingState (
    val topic: String = "",
    val speakingText: String = "",
    val aiGeneratedText: String = "",
    val isPostShared: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)