package com.better.betterapp.feature_speaking.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.better.betterapp.R
import com.better.betterapp.core.presentation.components.TopicDialog
import com.better.betterapp.feature_home.presentation.components.ErrorText
import com.better.betterapp.feature_home.presentation.components.SpeakingPostItem
import com.better.betterapp.feature_login.LoginViewModel
import com.better.betterapp.feature_speaking.presentation.SpeakingEvent
import com.better.betterapp.feature_speaking.presentation.SpeakingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakingScreen(
    viewModel: SpeakingViewModel = hiltViewModel()
) {
    val state = viewModel.stateSpeaking.value
    val eventFlow = viewModel.eventFlow

    var showTopic by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        //navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Geri",
                            tint = TopAppBarDefaults.topAppBarColors().actionIconContentColor
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            showTopic = true
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.home),
                            contentDescription = "Konu",
                            tint = TopAppBarDefaults.topAppBarColors().actionIconContentColor
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(SpeakingEvent.Share)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.home),
                            contentDescription = "Paylaş",
                            tint = TopAppBarDefaults.topAppBarColors().actionIconContentColor
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LaunchedEffect(key1 = true) {
                eventFlow.collectLatest { event ->
                    when (event) {
                        is SpeakingViewModel.UiEvent.ShowSnackbar -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(event.message)
                            }
                        }

                        is SpeakingViewModel.UiEvent.Shared -> {
                            // popUp
                        }
                    }
                }
            }

            if (showTopic) {
                TopicDialog(topic = state.topic) {
                    showTopic = false
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
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (state.speakingText == "") {
                            Box {
                                Icon(
                                    painter = painterResource(R.drawable.home),
                                    contentDescription = "Mikrofon",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(80.dp)
                                        .clickable {
                                            // mikrofonu aç
                                        }
                                )
                            }
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                text = state.speakingText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Button(
                            onClick = { viewModel.onEvent(SpeakingEvent.Correct) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Düzelt")
                        }

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            text = state.aiGeneratedText,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}