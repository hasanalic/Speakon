package com.better.betterapp.feature_speaking.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SpeakingViewModel @Inject constructor(

): ViewModel() {

    private val _stateSpeaking = mutableStateOf(SpeakingState())
    var stateSpeaking: State<SpeakingState> = _stateSpeaking

    fun onEvent(event: SpeakingEvent) {
        when(event) {
            is SpeakingEvent.ChangeSpeakingText -> {
                changeSpeakingText(event.newText)
            }

            is SpeakingEvent.Correct -> {
                correctText()
            }

            is SpeakingEvent.Share -> {
                share()
            }
        }
    }

    private fun changeSpeakingText(newText: String) {
        _stateSpeaking.value = _stateSpeaking.value.copy(speakingText = newText)
    }

    private fun changeAiGeneratedText(newText: String) {
        _stateSpeaking.value = _stateSpeaking.value.copy(aiGeneratedText = newText)
    }

    private fun correctText() {
        val currentText = stateSpeaking.value.speakingText
        if (currentText.isBlank()) return

        _stateSpeaking.value = _stateSpeaking.value.copy(isLoading = true)

        viewModelScope.launch {

        }
    }

    private fun share() {
        val speakingText = stateSpeaking.value.speakingText
        val aiText = stateSpeaking.value.aiGeneratedText

        if (speakingText.isNotBlank() && aiText.isNotBlank()) {

        } else {

        }
    }
}