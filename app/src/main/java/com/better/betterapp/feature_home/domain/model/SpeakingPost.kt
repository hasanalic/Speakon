package com.better.betterapp.feature_home.domain.model

data class SpeakingPost(
    val postId: String,
    val userId: String,
    val topicId: String,
    val userName: String,
    val avatarId: Int,
    val speakingText: String,
    val postScore: Double,
    val coherance: Int,
    val grammer: Int,
    var fluency: Int,
    val createdAt: Long
)