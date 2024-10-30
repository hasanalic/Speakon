package com.better.betterapp.feature_home.domain.model

data class SpeakingPost(
    val postId: Int,
    val userName: String,
    val avatarId: Int,
    val speakingText: String,
    val postScore: String,      // ortalama puan
    val coherance: Int,         // tutarlılık puanı
    val grammer: Int,           // gramer puanı
    var fluency: Int,           // akıcılık puanı
    val createdAt: Long,
    val likes: Int
)
