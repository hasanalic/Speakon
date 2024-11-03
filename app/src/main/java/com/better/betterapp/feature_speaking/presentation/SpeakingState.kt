package com.better.betterapp.feature_speaking.presentation

data class SpeakingState (
    val topic: String = "",
    val speakingText: String = "",
    val aiGeneratedText: String = "",
    val averageSpeakingScore: Double? = null,
    val coheranceScore: Int? = null,
    val grammarScore: Int? = null,
    var fluencyScore: Int? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)