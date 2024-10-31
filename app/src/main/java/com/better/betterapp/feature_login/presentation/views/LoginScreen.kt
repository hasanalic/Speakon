package com.better.betterapp.feature_login.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.better.betterapp.R
import com.better.betterapp.feature_home.presentation.components.ErrorText
import com.better.betterapp.feature_home.presentation.util.AppDetails
import com.better.betterapp.feature_login.presentation.LoginEvent
import com.better.betterapp.feature_login.presentation.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateTo: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.stateLogin.value
    val eventFlow = viewModel.eventFlow

    val context = LocalContext.current

    val avatarIdList by remember {
        mutableStateOf(
            listOf(
                R.drawable.home,
                R.drawable.home,
                R.drawable.home,
                R.drawable.home,
                R.drawable.home,
                R.drawable.home,
                R.drawable.home,
                R.drawable.home
            )
        )
    }

    var showAvatarList by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LaunchedEffect(key1 = true) {
                eventFlow.collectLatest { event ->
                    when (event) {
                        is LoginViewModel.UiEvent.ShowSnackbar -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(event.message)
                            }
                        }

                        is LoginViewModel.UiEvent.Registered -> {
                            navigateTo()
                        }
                    }
                }
            }

            if (showAvatarList) {
                AvatarListDialog(
                    avatarIdList = avatarIdList,
                    onDismiss = { showAvatarList = false }
                ) {
                    viewModel.onEvent(LoginEvent.ChangeAvatar(it))
                }
            }

            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error.isNotBlank() -> {
                    ErrorText(error = state.error, modifier = Modifier.align(Alignment.Center))
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = AppDetails.getAppName(context),
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        Image(
                            painter = painterResource(id = if (state.avatarId != null) state.avatarId else R.drawable.ic_launcher_background),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .clickable {
                                    showAvatarList = true
                                }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        TextField(
                            value = state.userName,
                            onValueChange = { viewModel.onEvent(LoginEvent.ChangeUserName(it)) },
                            label = { Text("İsim") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = state.email,
                            onValueChange = { viewModel.onEvent(LoginEvent.ChangeEmail(it)) },
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = state.password,
                            onValueChange = { viewModel.onEvent(LoginEvent.ChangePassword(it)) },
                            label = { Text("Şifre") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.onEvent(LoginEvent.Register) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text(text = "Kayıt Ol")
                        }
                    }
                }
            }
        }
    }
}