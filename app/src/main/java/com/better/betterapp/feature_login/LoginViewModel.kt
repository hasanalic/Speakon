package com.better.betterapp.feature_login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class LoginViewModel @Inject constructor(

): ViewModel() {

    private val _stateLogin = mutableStateOf(LoginState())
    var stateLogin: State<LoginState> = _stateLogin


}