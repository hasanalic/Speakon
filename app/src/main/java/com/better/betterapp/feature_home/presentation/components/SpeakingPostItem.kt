package com.better.betterapp.feature_home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.better.betterapp.R
import com.better.betterapp.feature_home.domain.model.SpeakingPost
import com.better.betterapp.ui.theme.AppTheme

@Composable
fun SpeakingPostItem(
    speakingPost: SpeakingPost,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.home),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                Text(
                    text = speakingPost.userName,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = speakingPost.postScore.toString(),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = speakingPost.speakingText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RatingBox(label = "Tutarlılık", rating = speakingPost.coherance)
                RatingBox(label = "Gramer", rating = speakingPost.grammer)
                RatingBox(label = "Akıcılık", rating = speakingPost.fluency)
            }
        }
    }
}

@Composable
fun RatingBox(label: String, rating: Int) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = "$label: ",
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = rating.toString(),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal)
            )
        }
    }
}

@Preview
@Composable
private fun Test() {
    AppTheme {
        /*
        SpeakingPostItem(speakingPost = SpeakingPost(
            1,"Better App", 1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "8.8",6,4,9,1L,5
        )) {

        }

         */
    }
}