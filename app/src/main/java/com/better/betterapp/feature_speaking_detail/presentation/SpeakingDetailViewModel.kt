package com.better.betterapp.feature_speaking_detail.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_speaking_detail.domain.use_cases.GetSpeakingDetailUseCase
import com.better.betterapp.feature_speaking_detail.domain.use_cases.SpeakingDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SpeakingDetailViewModel @Inject constructor(
    private val speakingDetailUseCases: SpeakingDetailUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _stateSpeakingDetail = mutableStateOf(SpeakingDetailState())
    var stateSpeakingDetail: State<SpeakingDetailState> = _stateSpeakingDetail

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            getSpeakingDetail(postId)
        }
    }

    private var getSpeakingDetailJob: Job? = null

    private fun getSpeakingDetail(postId: String) {
        getSpeakingDetailJob?.cancel()
        getSpeakingDetailJob = speakingDetailUseCases.getSpeakingDetailUseCase(postId).onEach { result ->
            when(result) {
                is Result.Success -> {
                    _stateSpeakingDetail.value = SpeakingDetailState(speakingDetail = result.data)
                }

                is Result.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: SpeakingDetailEvent) {
        when(event) {
            is SpeakingDetailEvent.ToggleSpeakingText -> {
                val isSelectedTextHumanBased = _stateSpeakingDetail.value.isSelectedTextHumanBased
                _stateSpeakingDetail.value = _stateSpeakingDetail.value.copy(isSelectedTextHumanBased = !isSelectedTextHumanBased)
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Shared: UiEvent()
    }
}