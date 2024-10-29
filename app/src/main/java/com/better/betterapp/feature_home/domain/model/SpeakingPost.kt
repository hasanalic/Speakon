package com.better.betterapp.feature_home.domain.model

data class SpeakingPost(
    val postId: Int,
    val userName: String,
    val avatarId: Int,
    val speakingText: String,
    val average: String,    // ortalama puan
    val coherance: String,  // tutarlılık puanı
    val grammer: String,    // gramer puanı
    var fluency: String,    // akıcılık puanı
    val createdAt: Long,
    val likes: Int
)
