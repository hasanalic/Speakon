package com.better.betterapp.feature_home.data.dto

data class FirestoreSpeakingPost(
    var postId: String = "",
    val userId: String,
    val topicId: String,
    val avatarId: Int,
    val userName: String,
    val originalText: String,
    val correctedText: String?,
    val coheranceScore: Int,
    val grammarScore: Int,
    val fluencyScore: Int,
    val averageScore: Double,
    val createdAt: Long
)