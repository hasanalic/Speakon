package com.better.betterapp.feature_login

sealed class LoginEvent {
    data class ChangeAvatar(val avatarId: Int): LoginEvent()
    data class ChangeUserName(val newName: String): LoginEvent()
    data class ChangeEmail(val newEmail: String): LoginEvent()
    data class ChangePassword(val newPassword: String): LoginEvent()
    object Register : LoginEvent()
}