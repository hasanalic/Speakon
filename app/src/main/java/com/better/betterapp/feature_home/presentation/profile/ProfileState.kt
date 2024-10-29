package com.better.betterapp.feature_home.presentation.profile

import com.better.betterapp.feature_home.domain.model.Profile
import com.better.betterapp.feature_home.domain.model.SpeakingPost

data class ProfileState (
    val profile: Profile? = null,
    val usersSpeakingPosts: List<SpeakingPost> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)