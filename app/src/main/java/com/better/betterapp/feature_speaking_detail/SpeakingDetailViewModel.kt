package com.better.betterapp.feature_speaking_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class SpeakingDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>("postId")?.let { postId ->
            // postId kullanılarak veri çekilir
            println(postId)
        }
    }

}