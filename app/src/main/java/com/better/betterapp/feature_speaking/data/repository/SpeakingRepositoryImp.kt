package com.better.betterapp.feature_speaking.data.repository

import com.better.betterapp.core.data.Collections
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Prompts
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.data.dto.FirestoreSpeakingPost
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_speaking.data.CorrectedTextResult
import com.better.betterapp.feature_speaking.domain.repository.SpeakingRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SpeakingRepositoryImp @Inject constructor(
    private val db: FirebaseFirestore
): SpeakingRepository {
    override fun correctText(speakingText: String, topic: String): Flow<Result<CorrectedTextResult, DataError.Network>> = flow {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "AIzaSyDcWEwm6iaPPN1Pdb_5GYbkYshjjU77hQo"
        )

        val prompt = Prompts.correctSpeakingText(speakingText, topic)

        try {
            val response = generativeModel.generateContent(prompt)
            println(response.text)

            var correctedText = Regex("Corrected Text: (.+)").find(response.text!!)?.groupValues?.get(1)?.trim() ?: ""
            val coherenceScore = Regex("Coherence Score: (\\d+)").find(response.text!!)?.groupValues?.get(1)?.toInt() ?: 0
            val grammarScore = Regex("Grammar Score: (\\d+)").find(response.text!!)?.groupValues?.get(1)?.toInt() ?: 0
            val fluencyScore = Regex("Fluency Score: (\\d+)").find(response.text!!)?.groupValues?.get(1)?.toInt() ?: 0

            correctedText = trimQuotes(correctedText)

            emit(Result.Success(
                CorrectedTextResult(
                    correctedText = correctedText,
                    coheranceScore = coherenceScore,
                    grammarScore = grammarScore,
                    fluencyScore = fluencyScore
                )
            ))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }

    private fun trimQuotes(text: String): String {
        return text.removePrefix("\"").removeSuffix("\"")
    }

    override fun publishSpeaking(speakingPost: SpeakingPost, aiCorrectedText: String): Flow<Result<Unit, DataError.Network>> = flow {
        try {
            val userId = speakingPost.userId

            val userSnapshot = db.collection(Collections.USERS).document(userId).get().await()

            val avatarId = userSnapshot.getLong("avatarId")?.toInt() ?: -1
            val userName = userSnapshot.getString("userName") ?: "-1"

            if (avatarId == -1 || userName == "-1") {
                emit(Result.Error(DataError.Network.NOT_FOUND))
                return@flow
            }

            val firestoreSpeakingPost = FirestoreSpeakingPost(
                userId = speakingPost.userId,
                topicId = speakingPost.topicId,
                avatarId = avatarId,
                userName = userName,
                originalText = speakingPost.speakingText,
                correctedText = aiCorrectedText,
                coheranceScore = speakingPost.coheranceScore,
                grammarScore = speakingPost.grammarScore,
                fluencyScore = speakingPost.fluencyScore,
                averageSpeakingScore = speakingPost.averageSpeakingScore,
                createdAt = speakingPost.createdAt
            )

            val documentRef = db.collection(Collections.SPEAKING_POSTS)
                .add(firestoreSpeakingPost)
                .await()

            documentRef.update("postId", documentRef.id).await()

            incrementUserConsecutiveDays(speakingPost.userId)
            updateUserAverageScore(speakingPost.userId)

            emit(Result.Success(Unit))

        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
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

        val totalScore = speakingPosts.documents.sumOf { it.getDouble("averageSpeakingScore") ?: 0.0 }
        val speakingPostCount = speakingPosts.size()
        val newAverageSpeakingScore = if (speakingPostCount > 0) totalScore / speakingPostCount else 0.0

        val highestConsecutiveDays = userRef.get().await().getLong("highestConsecutiveDays")?.toInt() ?: 0

        val averageScoreMultiplier = 0.4
        val consecutiveDaysMultiplier = 0.6

        val newAverageScore = (newAverageSpeakingScore * averageScoreMultiplier) + (highestConsecutiveDays * consecutiveDaysMultiplier)

        userRef.update(
            mapOf(
                "averageSpeakingScore" to newAverageSpeakingScore,
                "averageScore" to newAverageScore
            )
        ).await()
    }

    override fun getDailyTopic(topicId: String): Flow<Result<String, DataError.Network>> = flow {
        try {
            val topicSnapshot = db.collection(Collections.TOPICS)
                .whereEqualTo("topicId", topicId)
                .get()
                .await()

            val topicText = topicSnapshot.documents.firstOrNull()?.getString("topic")

            if (topicText != null) {
                emit(Result.Success(topicText))
            } else {
                emit(Result.Error(DataError.Network.NOT_FOUND))
            }
        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }
}