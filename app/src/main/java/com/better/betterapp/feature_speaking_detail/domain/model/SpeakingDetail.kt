package com.better.betterapp.feature_speaking_detail.domain.model

data class SpeakingDetail (
    val postId: Int,
    val userName: String,
    val avatarId: Int,
    val topicText: String,
    val speakingText: String,
    val aiCorrectedText: String,
    val postScore: String,
    val coherance: Int,
    val grammer: Int,
    var fluency: Int,
    val createdAt: Long,
    val likes: Int
)