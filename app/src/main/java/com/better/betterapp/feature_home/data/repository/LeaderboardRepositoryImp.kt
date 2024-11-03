package com.better.betterapp.feature_home.data.repository

import com.better.betterapp.core.data.Collections
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_home.domain.model.User
import com.better.betterapp.feature_home.domain.repository.LeaderboardRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LeaderboardRepositoryImp @Inject constructor(
    private val db: FirebaseFirestore
): LeaderboardRepository {
    override fun getUsers(): Flow<Result<List<User>, DataError.Network>> = flow {
        try {
            val usersSnapshot = db.collection(Collections.USERS)
                .orderBy("averageScore", Query.Direction.DESCENDING)
                .get()
                .await()

            val userList = usersSnapshot.documents.mapNotNull { document ->
                val userId = document.getString("userId") ?: return@mapNotNull null
                val userName = document.getString("userName") ?: return@mapNotNull null
                val avatarId = document.getLong("avatarId")?.toInt() ?: return@mapNotNull null
                val averageScore = document.getLong("averageScore")?.toInt() ?: 0

                println("AVATAR ID LEADERBOARD -> $avatarId")

                User(
                    userId = userId,
                    userName = userName,
                    avatarId = avatarId,
                    score = averageScore.toString()
                )
            }

            emit(Result.Success(userList))

        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }
}