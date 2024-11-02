package com.better.betterapp.feature_home.data.mapper

import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.google.firebase.firestore.DocumentSnapshot

fun mapToSpeakingPost(document: DocumentSnapshot): SpeakingPost {
    return SpeakingPost(
        postId = document.getString("postId") ?: "",
        userId = document.getString("userId") ?: "",
        topicId = document.getString("topicId") ?: "",
        userName = document.getString("userName") ?: "",
        avatarId = document.getLong("avatarId")?.toInt() ?: 0,
        speakingText = document.getString("originalText") ?: "",
        averageSpeakingScore = document.getDouble("averageSpeakingScore") ?: 0.0,
        coheranceScore = document.getLong("coheranceScore")?.toInt() ?: 0,
        grammarScore = document.getLong("grammarScore")?.toInt() ?: 0,
        fluencyScore = document.getLong("fluencyScore")?.toInt() ?: 0,
        createdAt = document.getLong("createdAt") ?: 0L
    )
}