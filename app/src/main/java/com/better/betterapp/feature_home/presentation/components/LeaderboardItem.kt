package com.better.betterapp.feature_home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.better.betterapp.R
import com.better.betterapp.feature_home.domain.model.User
import com.better.betterapp.ui.theme.AppTheme

@Composable
fun LeaderboardItem(
    user: User,
    rank: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$rank.",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(user.avatarId),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = user.userName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = user.score.toString(),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
private fun Test() {
    AppTheme {
        LeaderboardItem(user = User("1","Better App", 1, "8.9"), rank = 1)
    }
}