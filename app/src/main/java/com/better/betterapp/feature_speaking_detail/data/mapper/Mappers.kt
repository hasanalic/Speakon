package com.better.betterapp.feature_speaking_detail.data.mapper

import com.better.betterapp.feature_speaking_detail.domain.model.SpeakingDetail
import com.google.firebase.firestore.DocumentSnapshot

fun mapToSpeakingDetail(document: DocumentSnapshot, topicText: String): SpeakingDetail {
    return SpeakingDetail(
        postId = document.getString("postId")?.toIntOrNull() ?: 0,
        userName = document.getString("userName") ?: "",
        avatarId = document.getLong("avatarId")?.toInt() ?: 0,
        topicText = topicText,
        speakingText = document.getString("originalText") ?: "",
        aiCorrectedText = document.getString("correctedText") ?: "",
        averageSpeakingScore = document.getDouble("averageSpeakingScore")?.toString() ?: "0.0",
        coheranceScore = document.getLong("coheranceScore")?.toInt() ?: 0,
        grammarScore = document.getLong("grammarScore")?.toInt() ?: 0,
        fluencyScore = document.getLong("fluencyScore")?.toInt() ?: 0,
        createdAt = document.getLong("createdAt") ?: 0L
    )
}