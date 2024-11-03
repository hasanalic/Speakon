package com.better.betterapp.core.presentation.navigation.home

import androidx.annotation.DrawableRes
import com.better.betterapp.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Ana Sayfa",
        icon = R.drawable.home
    )

    object Leaderboard: BottomBarScreen(
        route = "leaderboard",
        title = "Skor Tablosu",
        icon = R.drawable.leaderboard
    )

    object Profile: BottomBarScreen(
        route = "profile",
        title = "Profil",
        icon = R.drawable.profile
    )
}