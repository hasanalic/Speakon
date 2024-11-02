package com.better.betterapp.feature_login.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun registerUser(avatarId: Int, userName: String, email: String, password: String): Flow<Result<Unit, DataError.Network>>
}