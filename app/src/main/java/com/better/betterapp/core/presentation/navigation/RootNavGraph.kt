package com.better.betterapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
//            OnboardingScreen(navigateTo = {
//                navController.navigate(Graph.PERMISSION) {
//                    popUpTo(Graph.ONBOARDING) { inclusive = true }
//                    launchSingleTop = true
//                }
//            })
        }

        composable(route = Graph.PERMISSION) {
//            PermissionsScreen(navigateTo = {
//                navController.navigate(Graph.HOME) {
//                    popUpTo(Graph.PERMISSION) { inclusive = true }
//                    launchSingleTop = true
//                }
//            })
        }

        composable(route = Graph.HOME) {
//            HomeScreen()
        }

        composable(route = Graph.SPEAKING_DETAIL) {
//            HomeScreen()
        }

        composable(route = Graph.SPEAKING) {
//            SpeakingScreen()
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
        composable(route = Graph.PERMISSION) {
//            PermissionsScreen(navigateTo = {
//                navController.navigate(Graph.HOME) {
//                    popUpTo(Graph.PERMISSION) { inclusive = true }
//                    launchSingleTop = true
//                }
//            })
        }

        composable(route = Graph.HOME) {
//            HomeScreen()
        }

        composable(route = Graph.SPEAKING_DETAIL) {
//            SpeakingDetailScreen()
        }

        composable(route = Graph.SPEAKING) {
//            SpeakingScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val ONBOARDING = "onboarding_graph"
    const val PERMISSION = "permission_graph"
    const val HOME = "home_graph"
    const val SPEAKING_DETAIL = "speaking_detail_graph"
    const val SPEAKING = "speaking_graph"
}