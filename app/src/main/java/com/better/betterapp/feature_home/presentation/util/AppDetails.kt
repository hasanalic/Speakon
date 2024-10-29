package com.better.betterapp.feature_home.presentation.util

import android.content.Context
import com.better.betterapp.R

class AppDetails {
    companion object {
        fun getAppName(context: Context) = context.getString(R.string.app_name)
    }
}