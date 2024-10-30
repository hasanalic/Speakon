package com.better.betterapp.feature_home.presentation.leaderboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LeaderboardScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: LeaderboardViewModel = hiltViewModel()
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "LEADERBOARD SCREEN", fontSize = 30.sp)
        }
    }

}