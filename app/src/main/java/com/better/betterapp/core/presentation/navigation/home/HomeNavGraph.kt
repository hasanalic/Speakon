package com.better.betterapp.core.presentation.navigation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.better.betterapp.core.presentation.navigation.Graph

@Composable
fun HomeNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
//            HomeScreen(navController = navController, paddingValues = paddingValues)
        }

        composable(route = BottomBarScreen.Leaderboard.route) {
//            LeaderboardScreen(navController = navController, paddingValues = paddingValues)
        }

        composable(route = BottomBarScreen.Profile.route) {
//            ProfileScreen(navController = navController, paddingValues = paddingValues)
        }
    }
}