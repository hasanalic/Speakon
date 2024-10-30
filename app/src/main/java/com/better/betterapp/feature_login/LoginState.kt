package com.better.betterapp.feature_login

data class LoginState (
    val avatarId: Int? = null,
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val isLoginSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)