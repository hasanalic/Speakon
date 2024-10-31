package com.better.betterapp.feature_login.domain.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result

interface LoginRepository {
    suspend fun registerUser(avatarId: Int, userName: String, email: String, password: String): Result<Unit, DataError.Network>
}