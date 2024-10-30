package com.better.betterapp.feature_speaking.presentation

sealed class SpeakingEvent {
    data class ChangeSpeakingText(val newText: String): SpeakingEvent()
    object Correct : SpeakingEvent()
    object Share : SpeakingEvent()
}