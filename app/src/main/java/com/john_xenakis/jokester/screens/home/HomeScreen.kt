package com.john_xenakis.jokester.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
import com.john_xenakis.jokester.data.models.JokeCategoryContent
import com.john_xenakis.jokester.data.models.JokeContent
import com.john_xenakis.jokester.ui.theme.NoColor
import com.john_xenakis.jokester.ui.theme.TranspColor
import john_xenakis.jokester.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

/*
    Jokester is the app for reading jokes and make people laugh.
    Copyright (C) 2022  Ioannis Xenakis

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

    Anything you want to contact me for, contact me with an e-mail, at: xenakis.i.contact@gmail.com
    I'll be happy to help you, or discuss anything with you!
 */

/**
 * The home screen.
 * @param homeViewModel The ViewModel for the home screen.
 */
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val selectedCategory = remember { mutableStateOf(JokeCategoryContent("Any")) }
    HomeUI(
        jokeList = remember { homeViewModel.jokeList },
        jokeCategoriesList = remember { homeViewModel.jokeCategoryList },
        loadJokeCategoryError = remember { homeViewModel.loadJokeCategoryError },
        isLoading = remember { homeViewModel.isLoading },
        loadError = remember { homeViewModel.loadError },
        errorCode = remember { homeViewModel.errorCode },
        loadJokeList = { homeViewModel.loadJokeList(
            selectedCategory.value.jokeCategoryText
        ) },
        clearJokeList = { homeViewModel.clearJokeList() },
        loadJokeCategories = { homeViewModel.loadJokeCategories() },
        selectedCategory = selectedCategory
    )
}

/**
 * The content/ui items of the home screen.
 * @param jokeList The list with its jokes.
 * @param jokeCategoriesList The list for the joke categories.
 * @param loadJokeCategoryError The message text for explaining the reason of the error,
 * for the joke category.
 * @param isLoading The boolean for determining if the jokes are still loading/fetching from the api.
 * @param loadError The message text for explaining the reason of the error for the jokes.
 * @param errorCode The error code number connected with the error.
 * @param loadJokeList The function for loading the joke list.
 * @param loadJokeCategories The function for loading the joke categories.
 * @param selectedCategory The joke category that is being selected to show its jokes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUI(
    jokeList: MutableState<List<JokeContent>>,
    jokeCategoriesList: MutableState<List<JokeCategoryContent>>,
    loadJokeCategoryError: MutableState<String>,
    isLoading: MutableState<Boolean>,
    loadError: MutableState<String>,
    errorCode: MutableState<Int>,
    loadJokeList: () -> Unit = {},
    clearJokeList: () -> Unit = {},
    loadJokeCategories: () -> Unit = {},
    selectedCategory: MutableState<JokeCategoryContent>
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawer(
        jokeCategoriesList = jokeCategoriesList.value,
        loadJokeCategoryError = loadJokeCategoryError.value,
        loadJokeList = { loadJokeList() },
        clearJokeList = { clearJokeList() },
        loadJokeCategories = { loadJokeCategories() },
        drawerState = drawerState,
        scope = scope,
        selectedCategory = selectedCategory
    ) {
        Scaffold(topBar = { TopAppBar(drawerState, scope) }) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                JokesScroller(
                    errorCode = errorCode.value,
                    loadError = loadError.value,
                    isLoading = isLoading.value,
                    jokeList = jokeList.value,
                    loadJokeList = { loadJokeList() },
                    loadJokeCategories = { loadJokeCategories() },
                    scope = scope
                )
            }
        }
    }
}

/**
 * The jokes scroller that displays each joke to the screen.
 * @param errorCode The code number for the error.
 * @param loadError The message text explaining the reason of the error, for the jokes.
 * @param isLoading The boolean for determining if the jokes
 * are still loading/fetching from the api.
 * @param jokeList The list containing the jokes.
 * @param loadJokeList The function for loading the joke list.
 * @param scope The scope for the coroutine.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun JokesScroller(
    errorCode: Int,
    loadError: String,
    isLoading: Boolean,
    jokeList: List<JokeContent>,
    loadJokeList: () -> Unit,
    loadJokeCategories: () -> Unit,
    scope: CoroutineScope = rememberCoroutineScope()
) {

    val pagerState = rememberPagerState()

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

                    DisplayJokeProgressIndicator(isLoading, true)
                    DisplayRetrySection(
                        loadError = loadError,
                        errorCode = errorCode,
                        isLoading = isLoading
                    ) {
                        loadJokeList()
                    }
                }
            }
        }

        // Next joke button.
        CreateIconButton(
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
        CreateIconButton(
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
            loadJokeList()
        }
    }

    DisplayRetrySection(
        loadError = loadError,
        errorCode = errorCode,
        isLoading = isLoading,
        jokeListIsEmpty = jokeList.isEmpty()
    ) {
        loadJokeList()
        loadJokeCategories()
    }

    Timber.d("isLoading: $isLoading")
    DisplayJokeProgressIndicator(isLoading, jokeList.isEmpty())

}

/**
 * Creates an IconButton.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CreateIconButton(
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
fun DisplayJokeProgressIndicator(
    isLoading: Boolean,
    jokeListIsEmpty: Boolean = true) {
    if (isLoading && jokeListIsEmpty) {
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
 * when error is occurred and getting list, is not successful.
 */
@Composable
fun DisplayRetrySection(
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
fun TopAppBar(
    drawerState: DrawerState,
    scope: CoroutineScope
) {
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
        navigationIcon = {
            IconButton(
                onClick = { scope.launch { drawerState.open() } },
                Modifier.semantics {
                    contentDescription = "Navigation button on top app bar"
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.nav_icon
                    ),
                    contentDescription = "Navigation icon for navigation button",
                    tint = MaterialTheme.colorScheme.onBackground)
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

/**
 * The side navigation drawer.
 * @param jokeCategoriesList The list containing the joke categories.
 * @param loadJokeCategoryError The message text for explaining the reason of the error,
 * for the joke categories.
 * @param loadJokeList The function for loading the joke list.
 * @param clearJokeList The function for clearing the jokes in the joke list.
 * @param loadJokeCategories The function for loading the joke categories.
 * @param scope The scope for the coroutine.
 * @param selectedCategory The joke category that is being selected.
 * @param drawerState The state of the navigation drawer.
 * @param content The other content of the home screen,
 * outside of the navigation drawer.
 */
@Composable
fun NavigationDrawer(
    jokeCategoriesList: List<JokeCategoryContent>,
    loadJokeCategoryError: String,
    loadJokeList: () -> Unit,
    clearJokeList: () -> Unit,
    loadJokeCategories: () -> Unit,
    scope: CoroutineScope,
    selectedCategory: MutableState<JokeCategoryContent> = mutableStateOf(
        JokeCategoryContent("Any")
    ),
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {

    ModalNavigationDrawer(
        modifier = Modifier
            .semantics { contentDescription = "Side navigation drawer" },
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(360.dp)
                    .semantics {
                        contentDescription = "Drawer sheet for navigation drawer"
                    },
                drawerShape = MaterialTheme.shapes.medium,
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .verticalScroll(state = rememberScrollState())
                        .semantics { contentDescription = "Drawer column" }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(56.dp)
                            .padding(
                                start = 16.dp,
                                end = 8.dp,
                                top = 8.dp
                            )
                            .semantics { contentDescription = "Drawer title" }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.laughing_icon
                            ),
                            contentDescription = "Laughing face icon in drawer",
                            modifier = Modifier
                                .size(43.dp),
                            tint = NoColor
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .semantics { contentDescription = "Drawer title text" },
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            text = "Jokester",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 18.dp,
                                bottom = 18.dp
                            )
                            .semantics {
                                contentDescription = "Drawer categories group title text"
                            },
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        text = "Joke categories",
                        style = MaterialTheme.typography.titleSmall
                    )

                    jokeCategoriesList.forEach { category ->
                        NavigationDrawerItem(
                            modifier = Modifier.semantics {
                                contentDescription = category.jokeCategoryText + " joke category"
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.joker_icon),
                                    contentDescription = "Joke categories icon, in drawer"
                                )
                            },
                            label = {
                                Text(
                                    text = category.jokeCategoryText,
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            },
                            selected = category == selectedCategory.value,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedCategory.value = category
                                clearJokeList()
                                loadJokeList()
                            },
                            colors = NavigationDrawerItemDefaults
                                .colors(
                                    selectedContainerColor = MaterialTheme
                                        .colorScheme
                                        .secondaryContainer,
                                    selectedIconColor = MaterialTheme
                                        .colorScheme
                                        .onSecondaryContainer,
                                    unselectedIconColor = MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant,
                                    selectedTextColor = MaterialTheme
                                        .colorScheme
                                        .onSecondaryContainer,
                                    unselectedTextColor = MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                                )
                        )
                    }

                    loadJokeCategories()

                    if (loadJokeCategoryError.isNotBlank()) {
                        Text(
                            modifier = Modifier.semantics {
                                contentDescription = "Joke categories error text, in navigation drawer"
                            },
                            text = loadJokeCategoryError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .semantics {
                                contentDescription = "Drawer items divider"
                            },
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 18.dp,
                                bottom = 18.dp
                            )
                            .semantics {
                                contentDescription = "Drawer other group title text"
                            },
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        text = "Other",
                        style = MaterialTheme.typography.titleSmall
                    )

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Support me",
                                modifier = Modifier.semantics {
                                    contentDescription = "Text for support me button"
                                },
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.titleSmall
                            )
                        },
                        selected = false,
                        onClick = { /*TODO Add onClick functionality for "support me" button.*/ },
                        modifier = Modifier.semantics {
                            contentDescription = "Support me button, in navigation drawer"
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.dollar_icon),
                                contentDescription = "Icon for support me button",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true,
        content = content
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
 * The light mode preview of how the home screen looks.
 */
@Composable
@Preview(showSystemUi = true)
fun HomeScreenLightPreview() {
    HomeUI(
        jokeList = remember {
            mutableStateOf(
                listOf(
                    JokeContent("This is a test joke.")
                )
            )
                            },
        jokeCategoriesList = remember {
            mutableStateOf(
                listOf(
                    JokeCategoryContent("This is a joke category test.")
                )
            )
                                      },
        loadJokeCategoryError = remember {
            mutableStateOf("This is a Joke category error text.")
                                         },
        errorCode = remember { mutableStateOf(200) },
        isLoading = remember { mutableStateOf(false) },
        loadError = remember { mutableStateOf("") },
        selectedCategory = remember {
            mutableStateOf(
                JokeCategoryContent("This is a joke category test.")
            )
        }
    )
}

/**
 * The dark mode preview of how the home screen looks.
 */
@Composable
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
fun HomeScreenDarkPreview() {
    HomeUI(
        jokeList = remember {
            mutableStateOf(
                listOf(
                    JokeContent("This is a test joke.")
                )
            )
        },
        jokeCategoriesList = remember {
            mutableStateOf(
                listOf(
                    JokeCategoryContent("This is a joke category test.")
                )
            )
        },
        loadJokeCategoryError = remember {
            mutableStateOf("This is a Joke category error text.")
        },
        errorCode = remember { mutableStateOf(200) },
        isLoading = remember { mutableStateOf(false) },
        loadError = remember { mutableStateOf("") },
        selectedCategory = remember {
            mutableStateOf(
                JokeCategoryContent("This is a joke category test.")
            )
        }
    )
}

/**
 * The light theme of how the navigation drawer looks.
 */
@Composable
@Preview(showSystemUi = true)
fun NavigationDrawerPreview() {
    NavigationDrawer(
        jokeCategoriesList = listOf(
            JokeCategoryContent("This is a joke category."),
            JokeCategoryContent("This is a 2nd joke category.")
        ),
        loadJokeCategoryError = "This is a joke category error text.",
        loadJokeList = {},
        clearJokeList = {},
        loadJokeCategories = {},
        scope = rememberCoroutineScope(),
        selectedCategory = remember {
            mutableStateOf(
                JokeCategoryContent("This is a joke category.")
            )
        },
        drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    ) {}
}