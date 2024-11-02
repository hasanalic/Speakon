package com.better.betterapp.feature_speaking_detail.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.better.betterapp.R
import com.better.betterapp.feature_home.presentation.components.ErrorText
import com.better.betterapp.feature_home.presentation.components.RatingBox
import com.better.betterapp.feature_speaking_detail.presentation.SpeakingDetailEvent
import com.better.betterapp.feature_speaking_detail.presentation.SpeakingDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakingDetailScreen(
    viewModel: SpeakingDetailViewModel = hiltViewModel()
) {
    val state = viewModel.stateSpeakingDetail.value

    val context = LocalContext.current

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
//                    IconButton(
//                        onClick = {
//                            // paylaş
//                        }
//                    ) {
//                        Icon(
//                            modifier = Modifier.size(22.dp),
//                            imageVector = ImageVector.vectorResource(id = R.drawable.home),
//                            contentDescription = "Paylaş",
//                            tint = TopAppBarDefaults.topAppBarColors().actionIconContentColor
//                        )
//                    }
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
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error.isNotBlank() -> {
                    ErrorText(error = state.error, modifier = Modifier.align(Alignment.Center))
                }

                state.speakingDetail != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Row() {
                                Text(
                                    text = "Konu: ",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Text(
                                    text = state.speakingDetail.topicText,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(vertical = 2.dp))

                        Card (
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RatingBox(label = "Tutarlılık", rating = state.speakingDetail.coheranceScore)
                                RatingBox(label = "Gramer", rating = state.speakingDetail.grammarScore)
                                RatingBox(label = "Akıcılık", rating = state.speakingDetail.fluencyScore)
                            }
                        }

                        Spacer(modifier = Modifier.padding(vertical = 2.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(SpeakingDetailEvent.ToggleSpeakingText)
                                },
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                text = if (state.isSelectedTextHumanBased) state.speakingDetail.speakingText else state.speakingDetail.aiCorrectedText,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.padding(vertical = 6.dp))

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            if (state.isSelectedTextHumanBased) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.home),
                                        contentDescription = "Avatar",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = state.speakingDetail.userName,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.home),
                                        contentDescription = "AI",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "Yapay Zeka",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}