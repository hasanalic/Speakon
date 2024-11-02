package com.better.betterapp.feature_login.data.repository

import com.better.betterapp.core.data.Collections
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_login.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): LoginRepository {

    override fun registerUser(
        avatarId: Int,
        userName: String,
        email: String,
        password: String
    ): Flow<Result<Unit, DataError.Network>> = flow {
        try {
            val userId = createFirebaseUser(email, password)
                ?: throw Exception("User ID creation failed in Firebase Authentication.")

            saveUserToFirestore(userId, avatarId, userName, email)

            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }

    private suspend fun createFirebaseUser(email: String, password: String): String? {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            authResult.user?.uid
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveUserToFirestore(
        userId: String,
        avatarId: Int,
        userName: String,
        email: String
    ) {
        val user = mapOf(
            "userId" to userId,
            "avatarId" to avatarId,
            "email" to email,
            "userName" to userName,
            "averageScore" to 0,
            "averageSpeakingScore" to 0.0,
            "highestConsecutiveDays" to 0
        )

        db.collection(Collections.USERS).document(userId).set(user).await()
    }
}