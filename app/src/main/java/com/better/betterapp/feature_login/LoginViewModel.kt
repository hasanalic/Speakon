package com.better.betterapp.feature_login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


class LoginViewModel @Inject constructor(

): ViewModel() {

    private val _stateLogin = mutableStateOf(LoginState())
    var stateLogin: State<LoginState> = _stateLogin

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.ChangeAvatar -> {
                _stateLogin.value = _stateLogin.value.copy(avatarId = event.avatarId)
            }
            is LoginEvent.ChangeEmail -> {
                _stateLogin.value = _stateLogin.value.copy(email = event.newEmail)
            }
            is LoginEvent.ChangePassword -> {
                _stateLogin.value = _stateLogin.value.copy(password = event.newPassword)
            }
            is LoginEvent.ChangeUserName -> {
                _stateLogin.value = _stateLogin.value.copy(userName = event.newName)
            }
            is LoginEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {

    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Registered: UiEvent()
    }
}