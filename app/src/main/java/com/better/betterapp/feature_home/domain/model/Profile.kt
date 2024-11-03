package com.better.betterapp.feature_home.domain.model

data class Profile (
    val userId: String,
    val userName: String,
    val avatarId: String,
    val averageScore: Number,
    val highestConsecutiveDays: Number
)