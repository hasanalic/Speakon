package com.better.betterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.better.betterapp.core.presentation.navigation.Graph
import com.better.betterapp.core.presentation.navigation.RootNavigationGrapgWithoutOnboarding
import com.better.betterapp.core.presentation.navigation.RootNavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onBoardingCompleted = false // customSharedPreferences.getOnBoardingState()!!
        val startDestination = Graph.HOME

        setContent {
            if (onBoardingCompleted) {
                RootNavigationGrapgWithoutOnboarding(navController = rememberNavController(), startDestination)
            } else {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}