package com.better.betterapp.feature_speaking_detail.domain.model

data class SpeakingDetail (
    val postId: Int,
    val userName: String,
    val avatarId: Int,
    val topicText: String,
    val speakingText: String,
    val aiCorrectedText: String,
    val averageSpeakingScore: String,
    val coheranceScore: Int,
    val grammarScore: Int,
    var fluencyScore: Int,
    val createdAt: Long
)