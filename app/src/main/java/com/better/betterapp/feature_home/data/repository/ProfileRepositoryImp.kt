package com.better.betterapp.feature_home.data.repository

import com.better.betterapp.core.data.Collections
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.Profile
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_home.domain.model.User
import com.better.betterapp.feature_home.domain.repository.ProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImp @Inject constructor(
    val db: FirebaseFirestore
): ProfileRepository{
    override fun getProfile(userId: String): Flow<Result<Profile, DataError.Network>> = flow {
        try {
            val userSnapshot = db.collection(Collections.USERS).document(userId).get().await()

            val profile = userSnapshot.data?.let { data ->
                Profile(
                    userId = data["userId"] as? String ?: "",
                    userName = data["userName"] as? String ?: "",
                    avatarId = (data["avatarId"] as? Int)?.toString() ?: "",
                    averageScore = data["averageScore"] as? Number ?: 0,
                    highestConsecutiveDays = data["highestConsecutiveDays"] as? Number ?: 0
                )
            } ?: throw Exception("User not found")

            emit(Result.Success(profile))

        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }

    override fun getUsersPosts(userId: String): Flow<Result<List<SpeakingPost>, DataError.Network>> = flow {
        try {
            val postsSnapshot = db.collection(Collections.SPEAKING_POSTS)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val speakingPosts = postsSnapshot.documents.mapNotNull { document ->
                val postId = document.getString("postId") ?: return@mapNotNull null
                val topicId = document.getString("topicId") ?: return@mapNotNull null
                val userName = document.getString("userName") ?: return@mapNotNull null
                val avatarId = document.getLong("avatarId")?.toInt() ?: return@mapNotNull null
                val originalText = document.getString("originalText") ?: return@mapNotNull null
                val correctedText = document.getString("correctedText") ?: ""
                val coheranceScore = document.getLong("coheranceScore")?.toInt() ?: 0
                val grammarScore = document.getLong("grammarScore")?.toInt() ?: 0
                val fluencyScore = document.getLong("fluencyScore")?.toInt() ?: 0
                val averageSpeakingScore = document.getDouble("averageSpeakingScore") ?: 0.0
                val createdAt = document.getLong("createdAt") ?: 0L

                SpeakingPost(
                    postId = postId,
                    userId = userId,
                    topicId = topicId,
                    userName = userName,
                    avatarId = avatarId,
                    speakingText = originalText,
                    averageSpeakingScore = averageSpeakingScore,
                    coheranceScore = coheranceScore,
                    grammarScore = grammarScore,
                    fluencyScore = fluencyScore,
                    createdAt = createdAt
                )
            }

            emit(Result.Success(speakingPosts))

        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }
}