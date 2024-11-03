package com.better.betterapp.feature_speaking.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_speaking.domain.use_cases.SpeakingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeakingViewModel @Inject constructor(
    private val speakingUseCases: SpeakingUseCases
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

    private var correctTextJob: Job? = null

    private fun correctText() {
        correctTextJob?.cancel()

        val currentSpeakingText = _stateSpeaking.value.speakingText
//        val topic = _stateSpeaking.value.topic
        val topic = "What is your number one goal for this year?"

        _stateSpeaking.value = _stateSpeaking.value.copy(isLoading = true)

        correctTextJob = speakingUseCases.correctTextUseCase(currentSpeakingText, topic).onEach { result ->
            when(result) {
                is Result.Success -> {
                    val correctedTextResult = result.data

                    val averageSpeakingScore = (correctedTextResult.coheranceScore + correctedTextResult.grammarScore + correctedTextResult.fluencyScore) / 3.0

                    println("aiGeneratedText -> ${correctedTextResult.correctedText}")
                    println("coheranceScore -> ${correctedTextResult.coheranceScore}")
                    println("grammarScore -> ${correctedTextResult.grammarScore}")
                    println("fluencyScore -> ${correctedTextResult.fluencyScore}")
                    println("averageSpeakingScore -> ${averageSpeakingScore}")

                    _stateSpeaking.value = _stateSpeaking.value.copy(
                        aiGeneratedText = correctedTextResult.correctedText,
                        coheranceScore = correctedTextResult.coheranceScore,
                        grammarScore = correctedTextResult.grammarScore,
                        fluencyScore = correctedTextResult.fluencyScore,
                        averageSpeakingScore = averageSpeakingScore,
                        isLoading = false
                    )
                }

                is Result.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(message = "Bir hata meydana geldi.")
                    )
                }
            }
        }.launchIn(viewModelScope)
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