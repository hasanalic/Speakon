package com.better.betterapp.feature_home.data.repository

import com.better.betterapp.core.data.Collections
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.data.mapper.mapToSpeakingPost
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.feature_home.domain.repository.HomeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val db: FirebaseFirestore
): HomeRepository {
    override fun getSpeakingPosts(): Flow<Result<List<SpeakingPost>, DataError.Network>> = flow {
        try {
            val snapshot = db.collection(Collections.SPEAKING_POSTS)
                .get()
                .await()

            val speakingPosts = snapshot.documents.mapNotNull { document ->
                mapToSpeakingPost(document)
            }

            emit(Result.Success(speakingPosts))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }

    override fun getDailyTopic(): Flow<Result<String, DataError.Network>> = flow {
        TODO("Not yet implemented")
    }
}