package com.better.betterapp.feature_speaking.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SpeakingViewModel @Inject constructor(

): ViewModel() {

    private val _stateSpeaking = mutableStateOf(SpeakingState())
    var stateSpeaking: State<SpeakingState> = _stateSpeaking

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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

        viewModelScope.launch {
            if (currentText.isBlank()) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(message = "İlk önce konu ile alakalı konuşma yapın.")
                )
            } else {
                _stateSpeaking.value = _stateSpeaking.value.copy(isLoading = true)


            }
        }
    }

    private fun share() {
        val speakingText = stateSpeaking.value.speakingText
        val aiText = stateSpeaking.value.aiGeneratedText

        viewModelScope.launch {
            if (speakingText.isNotBlank() && aiText.isNotBlank()) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(message = "İki alan da dolu olmalıdır.")
                )
            } else {

            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Shared: UiEvent()
    }
}