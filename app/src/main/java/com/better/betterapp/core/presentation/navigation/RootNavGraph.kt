package com.better.betterapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.better.betterapp.core.presentation.navigation.home.HomeScreen
import com.better.betterapp.feature_login.presentation.views.LoginScreen
import com.better.betterapp.feature_magic.MagicScreen
import com.better.betterapp.feature_onboarding.OnboardingScreen
import com.better.betterapp.feature_speaking.presentation.views.SpeakingScreen
import com.better.betterapp.feature_speaking_detail.presentation.views.SpeakingDetailScreen

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
            HomeScreen(
                navigateToDetail = { postId ->
                    navController.navigate(Graph.SPEAKING_DETAIL + "?postId=${postId}")
                },
                navigateToSpeaking = {
                    navController.navigate(Graph.SPEAKING)
                }
            )
        }

        composable(
            route = Graph.SPEAKING_DETAIL + "?postId={postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) {
            SpeakingDetailScreen(
                navigateToMagic = { funId: Int, topic: String, speakingText: String, aiCorrectedText: String ->
                    navController.navigate(
                        "${Graph.MAGIC}/$funId/$topic/$speakingText/$aiCorrectedText"
                    )
                }
            )
        }

        composable(route = Graph.SPEAKING) {
            SpeakingScreen()
        }

        composable(
            route = "${Graph.MAGIC}/{funId}/{topic}/{speakingText}/{aiCorrectedText}",
            arguments = listOf(
                navArgument("funId") { type = NavType.IntType },
                navArgument("topic") { type = NavType.StringType },
                navArgument("speakingText") { type = NavType.StringType },
                navArgument("aiCorrectedText") { type = NavType.StringType }
            )
        ) {
            MagicScreen()
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
            HomeScreen(
                navigateToDetail = { postId ->
                    navController.navigate(Graph.SPEAKING_DETAIL + "?postId=${postId}")
                },
                navigateToSpeaking = {
                    navController.navigate(Graph.SPEAKING)
                }
            )
        }

        composable(
            route = Graph.SPEAKING_DETAIL + "?postId={postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) {
            SpeakingDetailScreen(
                navigateToMagic = { funId: Int, topic: String, speakingText: String, aiCorrectedText: String ->
                    navController.navigate(
                        "${Graph.MAGIC}/$funId/$topic/$speakingText/$aiCorrectedText"
                    )
                }
            )
        }

        composable(route = Graph.SPEAKING) {
            SpeakingScreen()
        }

        composable(
            route = "${Graph.MAGIC}/{funId}/{topic}/{speakingText}/{aiCorrectedText}",
            arguments = listOf(
                navArgument("funId") { type = NavType.IntType },
                navArgument("topic") { type = NavType.StringType },
                navArgument("speakingText") { type = NavType.StringType },
                navArgument("aiCorrectedText") { type = NavType.StringType }
            )
        ) {
            MagicScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val ONBOARDING = "onboarding_graph"
    const val LOGIN = "login_graph"
    const val HOME = "home_graph"
    const val SPEAKING_DETAIL = "speaking_detail_graph"
    const val MAGIC = "magic_graph"
    const val SPEAKING = "speaking_graph"
}