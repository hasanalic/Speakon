package com.better.betterapp.feature_home.domain.use_cases

data class HomeUseCases (
    val getSpeakingPostsUseCase: GetSpeakingPostsUseCase,
    val getDailyTopicUseCase: GetDailyTopicUseCase,
    val getUsersUseCase: GetUsersUseCase,
    val getProfileUseCase: GetProfileUseCase,
    val getUsersPostsUseCase: GetUserPostsUseCase
)