package com.better.betterapp.feature_login.domain.use_cases

import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class RegisterUserUseCase (
    private val loginRepository: LoginRepository
) {
    operator fun invoke(avatarId: Int, userName: String, email: String, password: String): Flow<Result<Unit, DataError.Network>> {
        return loginRepository.registerUser(avatarId, userName, email, password)
    }
}