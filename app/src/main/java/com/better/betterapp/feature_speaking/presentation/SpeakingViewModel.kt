package com.better.betterapp.feature_speaking.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_login.domain.use_cases.HomeUseCases
import com.better.betterapp.feature_speaking.domain.use_cases.SpeakingUseCases
import com.google.firebase.auth.FirebaseAuth
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
    private val speakingUseCases: SpeakingUseCases,
    private val homeUseCases: HomeUseCases
): ViewModel() {

    private val _stateSpeaking = mutableStateOf(SpeakingState())
    var stateSpeaking: State<SpeakingState> = _stateSpeaking

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getDailyTopic()
    }

    private var getDailyTopicJob: Job? = null

    private fun getDailyTopic() {
        val topicId = "aBaWq3T5VvixRucYms9k"
        getDailyTopicJob?.cancel()
        getDailyTopicJob = homeUseCases.getDailyTopicUseCase(topicId).onEach { result ->
            when(result) {
                is Result.Success -> {
                    _stateSpeaking.value = _stateSpeaking.value.copy(topic = result.data, topicId = topicId)
                }

                is Result.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

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
        val topic = _stateSpeaking.value.topic

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

    private var shareJob: Job? = null

    private fun share() {
        shareJob?.cancel()
        _stateSpeaking.value = _stateSpeaking.value.copy(isLoading = true)

        val speakingText = stateSpeaking.value.speakingText
        val aiText = stateSpeaking.value.aiGeneratedText

        if (speakingText.isBlank() || aiText.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(message = "İki alan da dolu olmalıdır.")
                )
            }
            return
        } else {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "-1"

            val speakingPost = SpeakingPost(
                postId = "",
                userId = userId,
                topicId = _stateSpeaking.value.topicId,
                userName = "",
                avatarId = 0,
                speakingText = _stateSpeaking.value.speakingText,
                averageSpeakingScore = _stateSpeaking.value.averageSpeakingScore!!,
                coheranceScore = _stateSpeaking.value.coheranceScore!!,
                grammarScore = _stateSpeaking.value.grammarScore!!,
                fluencyScore = _stateSpeaking.value.fluencyScore!!,
                createdAt = System.currentTimeMillis()
            )

            shareJob = speakingUseCases.publishSpeakingUseCase(speakingPost, aiCorrectedText = _stateSpeaking.value.aiGeneratedText).onEach { result ->
                when(result) {
                    is Result.Success -> {
                        _eventFlow.emit(
                            UiEvent.Shared
                        )
                    }

                    is Result.Error -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(message = "Gönderi paylaşılırken hata oluştu.")
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Shared: UiEvent()
    }
}