package com.better.betterapp.feature_home.data.dto

data class FirestoreUser (
    val userId: String,
    val avatarId: Int,
    val email: String,
    val averageScore: Int,
    val averageSpeakingScore: Double,
    val highestConsecutiveDays: Int
)