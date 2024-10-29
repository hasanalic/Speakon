package com.better.betterapp.core.presentation.shared_preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class CustomSharedPreferences {

    companion object {
        private const val PREFERENCE_ONBOARDING = "preference_onboarding"

        private var sharedPreferencesOnBoarding: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null

        operator fun invoke(context: Context): CustomSharedPreferences = instance ?: synchronized(Any()) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            sharedPreferencesOnBoarding = context.getSharedPreferences(PREFERENCE_ONBOARDING, Context.MODE_PRIVATE)
            return CustomSharedPreferences()
        }
    }

    fun setOnBoardingState(completed: Boolean) {
        sharedPreferencesOnBoarding?.edit(commit = true) {
            putBoolean("completed", completed)
        }
    }

    fun getOnBoardingState() = sharedPreferencesOnBoarding?.getBoolean("completed", false)
}