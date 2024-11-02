package com.better.betterapp.feature_speaking_detail.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SpeakingDetailViewModel @Inject constructor(
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