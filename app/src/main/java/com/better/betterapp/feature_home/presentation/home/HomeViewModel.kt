package com.better.betterapp.feature_home.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _stateHome = mutableStateOf(HomeState())
    val stateHome: State<HomeState> = _stateHome

    init {
        getSpeakingPosts()
    }

    private var getSpeakingPostsJob: Job? = null

    private fun getSpeakingPosts() {

    }
}