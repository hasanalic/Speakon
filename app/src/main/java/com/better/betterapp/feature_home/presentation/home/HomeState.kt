package com.better.betterapp.feature_home.presentation.home

import com.better.betterapp.feature_home.domain.model.SpeakingPost

data class HomeState (
    val speakingPosts: List<SpeakingPost> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)