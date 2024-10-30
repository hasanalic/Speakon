package com.better.betterapp.feature_home.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class ProfileViewModel @Inject constructor(

) : ViewModel() {

    private val _stateProfile = mutableStateOf(ProfileState())
    val stateProfile: State<ProfileState> = _stateProfile

    init {
        getProfileAndSpeakingPosts()
    }

    private fun getProfileAndSpeakingPosts() {

    }
}