package com.better.betterapp.feature_speaking.presentation.views

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.better.betterapp.MainActivity
import com.better.betterapp.R
import com.better.betterapp.core.presentation.components.TopicDialog
import com.better.betterapp.feature_home.presentation.components.ErrorText
import com.better.betterapp.feature_home.presentation.components.RatingBox
import com.better.betterapp.feature_speaking.presentation.SpeakingEvent
import com.better.betterapp.feature_speaking.presentation.SpeakingViewModel
import com.better.betterapp.feature_speaking.presentation.parser.VoiceToTextParser
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakingScreen(
    viewModel: SpeakingViewModel = hiltViewModel()
) {
    val state = viewModel.stateSpeaking.value
    val eventFlow = viewModel.eventFlow

    val context = LocalContext.current as MainActivity

    val voiceToTextParser = remember {
        VoiceToTextParser(context.application)
    }
    
    var canRecord by remember {
        mutableStateOf(false)
    }
    
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            canRecord = isGranted
        }
    )

    val voiceState by voiceToTextParser.state.collectAsState()

    var showTopic by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (voiceState.isSpeaking) {
                        voiceToTextParser.stopListening()
                    } else {
                        voiceToTextParser.startListening()
                    }
                }
            ) {
                AnimatedContent(targetState = voiceState.isSpeaking) { isSpeaking ->
                    if (isSpeaking) {
                        Icon(imageVector = Icons.Rounded.Stop, contentDescription = "Dur")
                    } else {
                        Icon(imageVector = Icons.Rounded.Mic, contentDescription = "Dur")
                    }
                }
            }
        },
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
                            imageVector = ImageVector.vectorResource(id = R.drawable.question_mark),
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
                            imageVector = ImageVector.vectorResource(id = R.drawable.send),
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
                recordAudioLauncher.launch(android.Manifest.permission.RECORD_AUDIO)

                eventFlow.collectLatest { event ->
                    when (event) {
                        is SpeakingViewModel.UiEvent.ShowSnackbar -> {
                            coroutineScope.launch {
                                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
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
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!voiceState.isSpeaking) {
                            viewModel.onEvent(SpeakingEvent.ChangeSpeakingText(voiceState.spokenText))
                        }

                        if (state.speakingText == "") {
                            Card(modifier = Modifier
                                .fillMaxWidth(),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "Sağ alttaki mikrofona tıklayarak günün konusu ile alakalı bir konuşma yap.",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            Card(modifier = Modifier
                                .fillMaxWidth(),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = state.speakingText,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                if (state.coheranceScore != null && state.grammarScore != null && state.fluencyScore != null) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        RatingBox(label = "Tutarlılık", rating = state.coheranceScore)
                                        RatingBox(label = "Gramer", rating = state.grammarScore)
                                        RatingBox(label = "Akıcılık", rating = state.fluencyScore ?: 0)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { viewModel.onEvent(SpeakingEvent.Correct) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Düzelt")
                        }

                        Card(modifier = Modifier
                            .fillMaxWidth(),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                text = state.aiGeneratedText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                    }
                }
            }
        }
    }
}