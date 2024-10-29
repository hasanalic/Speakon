package com.better.betterapp.feature_onboarding

import androidx.annotation.DrawableRes
import com.better.betterapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.home,
        title = "Lorem Ipsum",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )

    object Second : OnBoardingPage(
        image = R.drawable.home,
        title = "Lorem Ipsum",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )

    object Third : OnBoardingPage(
        image = R.drawable.home,
        title = "Lorem Ipsum",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
}