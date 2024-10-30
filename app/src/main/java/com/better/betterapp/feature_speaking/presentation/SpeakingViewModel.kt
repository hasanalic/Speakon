package com.better.betterapp.feature_speaking.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SpeakingViewModel @Inject constructor(

): ViewModel() {

    private val _stateSpeaking = mutableStateOf(SpeakingState())
    var stateSpeaking: State<SpeakingState> = _stateSpeaking

}