package com.better.betterapp.feature_login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    navigateTo: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "LOGIN SCREEN", fontSize = 30.sp)
            Button(onClick = { navigateTo() }) {
                Text(text = "Home Screen'e git", fontSize = 20.sp)
            }
        }
    }
}