package com.better.betterapp.feature_home.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _stateMain = mutableStateOf(MainState())
    val stateMain: State<MainState> = _stateMain

    init {
        getSpeakingPosts()
    }

    private var getSpeakingPostsJob: Job? = null

    private fun getSpeakingPosts() {

    }
}