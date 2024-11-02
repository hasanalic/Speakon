package com.better.betterapp.feature_home.domain.model

data class SpeakingPost(
    val postId: String,
    val userId: String,
    val topicId: String,
    val userName: String,
    val avatarId: Int,
    val speakingText: String,
    val averageSpeakingScore: Double,
    val coheranceScore: Int,
    val grammarScore: Int,
    var fluencyScore: Int,
    val createdAt: Long
)