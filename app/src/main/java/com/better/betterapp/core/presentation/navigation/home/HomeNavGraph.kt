package com.better.betterapp.core.presentation.navigation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.better.betterapp.core.presentation.navigation.Graph
import com.better.betterapp.feature_home.presentation.leaderboard.LeaderboardScreen
import com.better.betterapp.feature_home.presentation.main.views.MainScreen
import com.better.betterapp.feature_home.presentation.profile.views.ProfileScreen

@Composable
fun HomeNavGraph(navController: NavHostController, paddingValues: PaddingValues, navigateToDetail: (Int) -> Unit) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            MainScreen(paddingValues = paddingValues, navController = navController, navigateToDetail = navigateToDetail)
        }

        composable(route = BottomBarScreen.Leaderboard.route) {
            LeaderboardScreen(navController = navController, paddingValues = paddingValues)
        }

        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(navController = navController, paddingValues = paddingValues)
        }
    }
}