package com.better.betterapp.feature_login.data.repository

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_login.domain.repository.LoginRepository

class LoginRepositoryImp : LoginRepository {
    override suspend fun registerUser(
        avatarId: Int,
        userName: String,
        email: String,
        password: String
    ): Result<Unit, DataError.Network> {
        TODO("Not yet implemented")
    }
}