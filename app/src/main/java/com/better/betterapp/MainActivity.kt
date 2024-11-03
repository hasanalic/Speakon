package com.better.betterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.better.betterapp.core.presentation.navigation.Graph
import com.better.betterapp.core.presentation.navigation.RootNavigationGrapgWithoutOnboarding
import com.better.betterapp.core.presentation.navigation.RootNavigationGraph
import com.better.betterapp.core.presentation.shared_preference.CustomSharedPreferences
import com.better.betterapp.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val customSharedPreferences = CustomSharedPreferences(this)

        val onBoardingCompleted = customSharedPreferences.getOnBoardingState()!!
        val startDestination = if (isUserLoggedIn()) Graph.HOME else Graph.LOGIN
        //val startDestination = Graph.LOGIN

        setContent {
            AppTheme {
                if (onBoardingCompleted) {
                    RootNavigationGrapgWithoutOnboarding(navController = rememberNavController(), startDestination)
                } else {
                    RootNavigationGraph(navController = rememberNavController())
                }
            }
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}