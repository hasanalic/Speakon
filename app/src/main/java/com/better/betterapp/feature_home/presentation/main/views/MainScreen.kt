package com.better.betterapp.feature_home.presentation.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import com.better.betterapp.R
import com.better.betterapp.core.presentation.components.TopicDialog
import com.better.betterapp.feature_home.presentation.components.ErrorText
import com.better.betterapp.feature_home.presentation.components.SpeakingPostItem
import com.better.betterapp.feature_home.presentation.main.MainViewModel
import com.better.betterapp.feature_home.presentation.util.AppDetails.Companion.getAppName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    navigateToDetail: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.stateMain.value

    val context = LocalContext.current

    var showTopic by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        getAppName(context),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                    )
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
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
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

                state.speakingPosts.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = state.speakingPosts,
                            key = { speakingPost ->
                                speakingPost.postId
                            }
                        ) { speakingPost ->
                            SpeakingPostItem(
                                speakingPost = speakingPost,
                                onClick = {
                                    navigateToDetail(speakingPost.postId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}