package com.better.betterapp.feature_login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.better.betterapp.core.domain.model.DataError
import com.better.betterapp.core.domain.model.Result
import com.better.betterapp.feature_login.domain.use_cases.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
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

    private var registerUserJob: Job? = null

    private fun register() {
        val avatarId = _stateLogin.value.avatarId
        val userName = _stateLogin.value.userName
        val email = _stateLogin.value.email
        val password = _stateLogin.value.password

        if (avatarId == null ||
            userName == "" ||
            email == "" ||
            password == "") {
            _stateLogin.value = _stateLogin.value.copy(error = "Lütfen tüm alanları doldurun.")
            return
        }

        registerUserJob?.cancel()
        registerUserJob = loginUseCases.registerUserUseCase(avatarId, userName, email, password).onEach {  result ->
            when(result) {
                is Result.Success -> {
                    _eventFlow.emit(
                        UiEvent.Registered
                    )
                }
                is Result.Error -> {
                    handleRegisterUserError(result.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun handleRegisterUserError(error: DataError.Network) {
        val message = when(error) {
            DataError.Network.REQUEST_TIMEOUT -> "REQUEST_TIMEOUT"
            DataError.Network.TOO_MANY_REQUEST -> "TOO_MANY_REQUEST"
            DataError.Network.NO_INTERNET -> "NO_INTERNET"
            DataError.Network.PAYLOAD_TOO_LARGE -> "PAYLOAD_TOO_LARGE"
            DataError.Network.SERVER_ERROR -> "SERVER_ERROR"
            DataError.Network.SERIALIZATION -> "SERIALIZATION"
            DataError.Network.UNKNOWN -> "Bilinmeyen bir hata meydana geldi."
        }

        _eventFlow.emit(
            UiEvent.ShowSnackbar(message)
        )
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Registered: UiEvent()
    }
}