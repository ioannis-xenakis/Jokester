package com.john_xenakis.jokester

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * The home screen items.
 */
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 51.dp),
        contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .clip(
                    MaterialTheme.shapes.medium
                )
                .background(
                    MaterialTheme.colorScheme.primary
                ),
            contentAlignment = Alignment.Center,
        ) {

            Text(
                modifier = Modifier
                    .padding(
                        vertical = 25.dp,
                        horizontal = 15.dp
                    ),
                text = "Helvetica and Times New Roman walk into a bar. \n" +
                        "\n" +
                        "“Get out of here!”\n" +
                        "shouts the bartender.\n" +
                        "“We don’t serve your type!”",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * The Light mode preview of how the home screen looks.
 */
@Composable
@Preview(showSystemUi = true)
fun HomeScreenLightPreview() {
    HomeScreen()
}

/**
 * The Dark mode preview of how the home screen looks.
 */
@Composable
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
fun HomeScreenDarkPreview() {
    HomeScreen()
}