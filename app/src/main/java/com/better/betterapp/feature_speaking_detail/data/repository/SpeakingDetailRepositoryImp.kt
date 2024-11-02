package com.better.betterapp.feature_speaking_detail.data.repository

import com.better.betterapp.core.data.Collections
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_speaking_detail.data.mapper.mapToSpeakingDetail
import com.better.betterapp.feature_speaking_detail.domain.model.SpeakingDetail
import com.better.betterapp.feature_speaking_detail.domain.repository.SpeakingDetailRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SpeakingDetailRepositoryImp @Inject constructor(
    private val db: FirebaseFirestore
): SpeakingDetailRepository {
    override fun getSpeakingDetail(postId: String): Flow<Result<SpeakingDetail, DataError.Network>> = flow {
        try {
            val snapshot = db.collection(Collections.SPEAKING_POSTS)
                .whereEqualTo("postId", postId)
                .get()
                .await()
                .documents
                .firstOrNull()

            if (snapshot != null) {
                val topicId = snapshot.getString("topicId") ?: ""

                val topicSnapshot = db.collection(Collections.TOPICS)
                    .whereEqualTo("topicId", topicId)
                    .get()
                    .await()
                    .documents
                    .firstOrNull()

                val topicText = topicSnapshot?.getString("topic") ?: ""

                val speakingDetail = mapToSpeakingDetail(snapshot, topicText)
                emit(Result.Success(speakingDetail))
            } else {
                emit(Result.Error(DataError.Network.NOT_FOUND))
            }
        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }
}