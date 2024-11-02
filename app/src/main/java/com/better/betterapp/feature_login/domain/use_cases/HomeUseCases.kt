package com.better.betterapp.feature_login.domain.use_cases

import com.better.betterapp.feature_home.domain.use_cases.GetDailyTopicUseCase
import com.better.betterapp.feature_home.domain.use_cases.GetSpeakingPostsUseCase

data class HomeUseCases (
    val getSpeakingPostsUseCase: GetSpeakingPostsUseCase,
    val getDailyTopicUseCase: GetDailyTopicUseCase
)