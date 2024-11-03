package com.better.betterapp.feature_home.domain.model

data class Profile (
    val userId: String,
    val userName: String,
    val avatarId: Number,
    val averageScore: Number,
    val highestConsecutiveDays: Number
)