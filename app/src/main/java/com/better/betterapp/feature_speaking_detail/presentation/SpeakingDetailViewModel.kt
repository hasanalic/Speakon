package com.better.betterapp.feature_speaking_detail.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

class SpeakingDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateSpeakingDetail = mutableStateOf(SpeakingDetailState())
    var stateSpeakingDetail: State<SpeakingDetailState> = _stateSpeakingDetail

    init {
        savedStateHandle.get<Int>("postId")?.let { postId ->
            getSpeakingDetail(postId)
        }
    }

    private var getSpeakingDetailJob: Job? = null

    private fun getSpeakingDetail(postId: Int) {

    }
}