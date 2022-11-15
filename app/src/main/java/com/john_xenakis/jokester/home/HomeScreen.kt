package com.john_xenakis.jokester.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.john_xenakis.jokester.ui.theme.TranspColor
import john_xenakis.jokester.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * The home screen items.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(topBar = { topAppBar() }) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            JokesScroller()
        }
    }
}

/**
 * The jokes scroller that displays each joke to the screen.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun JokesScroller(homeViewModel: HomeViewModel = hiltViewModel()) {
    val jokeList by remember { homeViewModel.jokeList }
    val isLoading by remember { homeViewModel.isLoading }
    val loadError by remember { homeViewModel.loadError }
    val errorCode by remember { homeViewModel.errorCode }
    val runOnce by remember { homeViewModel.runOnce }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
        .fillMaxSize()
    ) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = "Joke horizontal pager" },
            count = jokeList.size,
            state = pagerState
        ) { page ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 51.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(
                                MaterialTheme.shapes.medium
                            )
                            .background(
                                MaterialTheme.colorScheme.primary
                            )
                            .testTag("Joke text box $page"),
                        contentAlignment = Alignment.Center,
                    ) {

                        Text(
                            modifier = Modifier
                                .padding(
                                    vertical = 25.dp,
                                    horizontal = 15.dp
                                )
                                .testTag("Joke text $page"),
                            text = jokeList[page].jokeText,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    displayJokeProgressIndicator(isLoading, false)
                    displayRetrySection(
                        loadError = loadError,
                        errorCode = errorCode,
                        isLoading = isLoading
                    ) {
                        homeViewModel.loadJokeList()
                    }
                }
            }
        }

        // Next joke button.
        createIconButton(
            scope = scope,
            pagerState = pagerState,
            alignment = Alignment.CenterEnd,
            enabledButton = pagerState.currentPage + 1 < jokeList.size,
            contentDescriptionButton = "Next joke button",
            contentDescriptionIcon = "Next joke icon",
            scrollToPage = pagerState.currentPage + 1,
            iconDrawableRes = R.drawable.ic_right_arrow_icon
        )

        // Previous joke button.
        createIconButton(
            scope = scope,
            pagerState = pagerState,
            alignment = Alignment.CenterStart,
            enabledButton = pagerState.currentPage + 1 > 1,
            contentDescriptionButton = "Previous joke button",
            contentDescriptionIcon = "Previous joke icon",
            scrollToPage = pagerState.currentPage - 1,
            iconDrawableRes = R.drawable.ic_left_arrow_icon
        )
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        printLogs(pagerState.currentPage, jokeList.size)
        if (pagerState.currentPage + 1 >= jokeList.size && !isLoading) {
            homeViewModel.loadJokeList()
        }
    }

    displayRetrySection(
        loadError = loadError,
        errorCode = errorCode,
        isLoading = isLoading,
        jokeListIsEmpty = jokeList.isEmpty()
    ) {
        homeViewModel.loadJokeList()
    }

    Timber.d("isLoading: $isLoading")
    displayJokeProgressIndicator(isLoading, runOnce)

}

/**
 * Creates an IconButton.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun createIconButton(
    scope: CoroutineScope,
    pagerState: PagerState,
    alignment: Alignment,
    enabledButton: Boolean,
    contentDescriptionIcon: String,
    contentDescriptionButton: String,
    scrollToPage: Int,
    iconDrawableRes: Int) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = alignment
    ) {

        IconButton(
            modifier = Modifier
                .size(48.dp)
                .semantics { contentDescription = contentDescriptionButton },
            onClick = {
                scope.launch {
                    if (enabledButton) {
                        pagerState.animateScrollToPage(
                            scrollToPage
                        )
                    }
                }
            },
            enabled = enabledButton
        ) {

            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(
                    id = iconDrawableRes
                ),
                tint = if (enabledButton) MaterialTheme.colorScheme.onBackground else TranspColor,
                contentDescription = contentDescriptionIcon
            )
        }
    }
}

/**
 * Display a circular progress indicator(a rotating circle),
 * when the joke list is loading.
 */
@Composable
fun displayJokeProgressIndicator(isLoading: Boolean, runOnce: Boolean) {
    if (isLoading && !runOnce) {
        LaunchedEffect(key1 = Unit) {
            Timber.d("Showing progress indicator.")
        }
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .semantics { contentDescription = "Joke progress indicator" },
            color = MaterialTheme
                .colorScheme
                .primary
        )
    }
}

/**
 * Display a retry section for the user to retry,
 * when error is occured and getting list, is not successful.
 */
@Composable
fun displayRetrySection(
    loadError: String,
    errorCode: Int = 0,
    isLoading: Boolean,
    jokeListIsEmpty: Boolean = true,
    onRetry: () -> Unit
) {
    Timber.d("isLoading: $isLoading")
    Timber.d("loadError: $loadError")
    if (!isLoading && loadError.isNotBlank() && jokeListIsEmpty) {
        Box(
            modifier = Modifier
                .testTag(
                    "Error box"
                ),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    modifier = Modifier.semantics { contentDescription = "Error text message" },
                    text = loadError,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )

                if (errorCode != 0) {
                    Text(
                        modifier = Modifier.semantics { contentDescription = "Error code" },
                        text = "Error code: $errorCode",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Button(
                    modifier = Modifier
                        .clip(
                            MaterialTheme.shapes.medium
                        )
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .semantics { contentDescription = "Retry button" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error),
                    onClick = { onRetry() }
                ) {

                    Text(
                        modifier = Modifier
                            .semantics { contentDescription = "Retry text" },
                        text = "Retry",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }
}

/**
 * The top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier.semantics {
            contentDescription = "Center aligned top app bar"
        },
        title = {
            Text(
                modifier = Modifier.semantics {
                    contentDescription = "Title text on top app bar"
                },
                text = "Jokes",
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults
            .smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
    )
}

/**
 * Prints the logs, for debugging purposes.
 */
private fun printLogs(
    currentPage: Int,
    jokeListSize: Int
) {
    Timber.d("Size of joke list: $jokeListSize")
    Timber.d("Current joke: ${currentPage + 1} \n \n")
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