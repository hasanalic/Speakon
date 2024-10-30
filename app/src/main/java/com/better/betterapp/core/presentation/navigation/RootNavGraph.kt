package com.better.betterapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.better.betterapp.core.presentation.navigation.home.HomeScreen
import com.better.betterapp.feature_login.LoginScreen
import com.better.betterapp.feature_onboarding.OnboardingScreen
import com.better.betterapp.feature_speaking.SpeakingScreen
import com.better.betterapp.feature_speaking_detail.SpeakingDetailScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.ONBOARDING
    ) {
        composable(route = Graph.ONBOARDING) {
            OnboardingScreen(navigateTo = {
                navController.navigate(Graph.LOGIN) {
                    popUpTo(Graph.ONBOARDING) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }

        composable(route = Graph.LOGIN) {
            LoginScreen(navigateTo = {
                navController.navigate(Graph.HOME) {
                    popUpTo(Graph.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }

        composable(route = Graph.HOME) {
            HomeScreen(navigateToDetail = { postId ->
                navController.navigate(Graph.SPEAKING_DETAIL + "?postId=${postId}")
            })
        }

        composable(
            route = Graph.SPEAKING_DETAIL + "?postId={postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) {
            SpeakingDetailScreen()
        }

        composable(route = Graph.SPEAKING) {
            SpeakingScreen()
        }
    }
}

@Composable
fun RootNavigationGrapgWithoutOnboarding(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestination
    ) {
        composable(route = Graph.LOGIN) {
            LoginScreen(navigateTo = {
                navController.navigate(Graph.HOME) {
                    popUpTo(Graph.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }

        composable(route = Graph.HOME) {
            HomeScreen(navigateToDetail = { postId ->
                navController.navigate(Graph.SPEAKING_DETAIL + "?postId=${postId}")
            })
        }

        composable(
            route = Graph.SPEAKING_DETAIL + "?postId={postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) {
            SpeakingDetailScreen()
        }

        composable(route = Graph.SPEAKING) {
            SpeakingScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val ONBOARDING = "onboarding_graph"
    const val LOGIN = "login_graph"
    const val HOME = "home_graph"
    const val SPEAKING_DETAIL = "speaking_detail_graph"
    const val SPEAKING = "speaking_graph"
}