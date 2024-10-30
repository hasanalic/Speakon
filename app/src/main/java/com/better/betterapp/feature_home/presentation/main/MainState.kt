package com.better.betterapp.feature_home.presentation.main

import com.better.betterapp.feature_home.domain.model.SpeakingPost

data class MainState (
    val speakingPosts: List<SpeakingPost> = emptyList(),
    val topic: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)