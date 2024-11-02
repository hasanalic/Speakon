package com.better.betterapp.feature_speaking.data.repository

import com.better.betterapp.core.data.Collections
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.data.dto.FirestoreSpeakingPost
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_speaking.domain.repository.SpeakingRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SpeakingRepositoryImp @Inject constructor(
    private val db: FirebaseFirestore
): SpeakingRepository {
    override suspend fun correctText(text: String): Result<String, DataError.Network> {
        // Gemini
        TODO("Not yet implemented")
    }

    override suspend fun publishSpeaking(speakingPost: SpeakingPost, aiCorrectedText: String): Result<Unit, DataError.Network> {
        return try {
            val postScore = (speakingPost.coheranceScore + speakingPost.grammarScore + speakingPost.fluencyScore) / 3.0

            val firestoreSpeakingPost = FirestoreSpeakingPost(
                userId = speakingPost.userId,
                topicId = speakingPost.postId,
                avatarId = speakingPost.avatarId,
                userName = speakingPost.userName,
                originalText = speakingPost.speakingText,
                correctedText = aiCorrectedText,
                coheranceScore = speakingPost.coheranceScore,
                grammarScore = speakingPost.grammarScore,
                fluencyScore = speakingPost.fluencyScore,
                averageSpeakingScore = postScore,
                createdAt = speakingPost.createdAt
            )

            val documentRef = db.collection(Collections.SPEAKING_POSTS)
                .add(firestoreSpeakingPost)
                .await()

            documentRef.update("postId", documentRef.id).await()

            incrementUserConsecutiveDays(speakingPost.userId)
            updateUserAverageScore(speakingPost.userId)

            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    private suspend fun incrementUserConsecutiveDays(userId: String) {
        val userRef = db.collection(Collections.USERS)
            .document(userId)
        userRef.update("highestConsecutiveDays", FieldValue.increment(1)).await()
    }

    private suspend fun updateUserAverageScore(userId: String) {
        val userRef = db.collection(Collections.USERS).document(userId)

        val speakingPosts = db.collection(Collections.SPEAKING_POSTS)
            .whereEqualTo("userId", userId)
            .get()
            .await()

        val totalScore = speakingPosts.documents.sumOf { it.getDouble("averageScore") ?: 0.0 }
        val speakingPostCount = speakingPosts.size()
        val newAverageScore = if (speakingPostCount > 0) totalScore / speakingPostCount else 0.0

        userRef.update("averageScore", newAverageScore).await()
        TODO("""
            averageScore -> averageSpeakingScore olsun.
            updateUserAverageScore fonksiyonu ilgili kişinin hem averageSpeakingScore hem de averageScore değerini güncellesin.
            updateUserAverageScore fonksiyonu ilgili kişinin averageSpeakingScore ve highestConsecutiveDays değerlerini alsın
            averageScore ise kullanıcının (averageSpeakingScore(8.8) * Y) + (highestConsecutiveDays * X) formülüyle
            hesaplanan ve leaderBoard'da çekerken kullanacağımız int bir sayı olsun. (sonuçlar ondalıklı çıkarsa tam sayıya çevrilir)
        """.trimIndent())
    }
}