package com.john_xenakis.jokester.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
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
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.john_xenakis.jokester.data.models.JokeCategoryContent
import com.john_xenakis.jokester.data.models.JokeContent
import com.john_xenakis.jokester.data.models.JokeFlagsContent
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
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val selectedCategory = remember { mutableStateOf(JokeCategoryContent("Any")) }

    val checkedFlagList = remember { homeViewModel.checkedFlagList }

    val safeMode = remember { homeViewModel.safeMode }

    initializeCheckableFlagStates(homeViewModel)

    putAllFlagStates(homeViewModel)

    HomeUI(
        jokeList = remember { homeViewModel.jokeList },
        jokeCategoriesList = remember { homeViewModel.jokeCategoryList },
        jokeFlagsList = remember { homeViewModel.jokeFlagsList },
        checkedFlagsState = homeViewModel.checkedFlagsState,
        checkedSafeFlagState = remember { mutableStateOf(false) },
        safeMode = safeMode,
        enabledFlagCheckbox = remember { mutableStateOf(true) },
        loadJokeCategoryError = remember { homeViewModel.loadJokeCategoryError },
        isLoading = remember { homeViewModel.isLoading },
        loadError = remember { homeViewModel.loadError },
        errorCode = remember { homeViewModel.errorCode },
        loadJokeList = { homeViewModel.loadJokeList(
            selectedCategory.value.jokeCategoryText,
            checkedFlagList.toList(),
            safeMode.value
        ) },
        clearJokeList = { homeViewModel.clearJokeList() },
        loadJokeCategories = { homeViewModel.loadJokeCategories() },
        loadJokeFlags = { homeViewModel.loadJokeFlags() },
        addToCheckedFlagsList = {
                jokeFlagsContent ->
            homeViewModel.addToCheckedFlagsList(jokeFlagsContent)
        },
        removeFromCheckedFlagsList = {
                jokeFlagsContent ->
            homeViewModel.removeFromCheckedFlagsList(jokeFlagsContent)
        },
        selectedCategory = selectedCategory,
        openDialog = remember { mutableStateOf( false ) },
        navToAboutScreen = { navController.navigate("about_screen") }
    )
}

/**
 * The content/ui items of the home screen.
 * @param jokeList The list with its jokes.
 * @param jokeCategoriesList The list for the joke categories.
 * @param jokeFlagsList The list for the joke flags.
 * @param checkedFlagsState The boolean states(Map) of if flags are checked or not.
 * @param checkedSafeFlagState The checked/unchecked state for safe mode checkbox.
 * @param safeMode The mode for showing only safe
 * and appropriate to everyone, jokes and filtering the unsafe ones.
 * @param loadJokeCategoryError The message text for explaining the reason of the error,
 * for the joke category.
 * @param isLoading The boolean for determining if the jokes are still loading/fetching from the api.
 * @param loadError The message text for explaining the reason of the error for the jokes.
 * @param errorCode The error code number connected with the error.
 * @param loadJokeList The function for loading the joke list.
 * @param loadJokeCategories The function for loading the joke categories.
 * @param loadJokeFlags The lambda block of loading joke flags.
 * @param addToCheckedFlagsList Adds checked joke flags to the checked flags list.
 * @param removeFromCheckedFlagsList Removes checked joke flag from the checked flags list.
 * @param selectedCategory The joke category that is being selected to show its jokes.
 * @param openDialog The boolean state of if filter jokes dialog is opened or not.
 */
@Composable
fun HomeUI(
    jokeList: MutableState<List<JokeContent>>,
    jokeCategoriesList: MutableState<List<JokeCategoryContent>>,
    jokeFlagsList: MutableState<List<JokeFlagsContent>> = mutableStateOf(listOf()),
    checkedFlagsState: MutableMap<Int, MutableState<Boolean>>,
    checkedSafeFlagState: MutableState<Boolean>,
    safeMode: MutableState<String?>,
    enabledFlagCheckbox: MutableState<Boolean>,
    loadJokeCategoryError: MutableState<String>,
    isLoading: MutableState<Boolean>,
    loadError: MutableState<String>,
    errorCode: MutableState<Int>,
    loadJokeList: () -> Unit = {},
    clearJokeList: () -> Unit = {},
    loadJokeCategories: () -> Unit = {},
    loadJokeFlags: () -> Unit = {},
    addToCheckedFlagsList: (JokeFlagsContent) -> Unit = {},
    removeFromCheckedFlagsList: (JokeFlagsContent) -> Unit = {},
    selectedCategory: MutableState<JokeCategoryContent>,
    openDialog: MutableState<Boolean>,
    navToAboutScreen: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawer(
        jokeCategoriesList = jokeCategoriesList.value,
        loadJokeCategoryError = loadJokeCategoryError.value,
        loadJokeList = { loadJokeList() },
        clearJokeList = { clearJokeList() },
        loadJokeCategories = { loadJokeCategories() },
        navToAboutScreen = { navToAboutScreen() },
        drawerState = drawerState,
        scope = scope,
        selectedCategory = selectedCategory
    ) {
        Scaffold(topBar = { TopAppBar(drawerState, scope, openDialog) }) { padding ->
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
                FilterJokesDialog(
                    openDialog = openDialog,
                    jokeFlagsList = jokeFlagsList,
                    checkedFlagsState = checkedFlagsState,
                    checkedSafeFlagState = checkedSafeFlagState,
                    enabledFlagCheckbox = enabledFlagCheckbox,
                    loadJokeFlags = loadJokeFlags,
                    safeMode = safeMode,
                    loadJokeList = {loadJokeList() },
                    clearJokeList = clearJokeList,
                    addToCheckedFlagsList = {jokeFlagsContent -> addToCheckedFlagsList(jokeFlagsContent)},
                    removeFromCheckedFlagsList = {jokeFlagsContent -> removeFromCheckedFlagsList(jokeFlagsContent) }
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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.laughing_man
                ),
                contentDescription = "Laughing man image",
                modifier = Modifier
                    .width(139.dp)
                    .height(127.dp),
                alignment = Alignment.BottomEnd
            )
        }

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
    scope: CoroutineScope,
    openDialog: MutableState<Boolean>
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
        actions = {
            IconButton(
                onClick = { openDialog.value = true },
                modifier = Modifier.semantics { contentDescription = "Filter jokes button on top bar" }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "Icon for filter jokes button on top bar",
                    tint = MaterialTheme.colorScheme.onBackground
                )
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
    navToAboutScreen: () -> Unit = {},
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
                        DrawerItem(
                            itemText = category.jokeCategoryText,
                            selectedBoolean = category == selectedCategory.value,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedCategory.value = category
                                clearJokeList()
                                loadJokeList()
                            },
                            itemDescription = category.jokeCategoryText + " joke category",
                            iconId = R.drawable.joker_icon,
                            iconDescription = "Joke categories icon, in drawer"
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

                    DrawerItem(
                        itemText = "About Jokester",
                        selectedBoolean = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navToAboutScreen()
                        },
                        itemDescription = "Drawer item About Jokester",
                        iconId = R.drawable.info_icon,
                        iconDescription = "Drawer item icon About Jokester"
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
 * Creates navigation drawer item.
 * @param itemText The item's text as a String.
 * @param selectedBoolean The boolean if the drawer item is selected or not.
 * @param onClick The block for managing the click functionality.
 * @param itemDescription The items content description, explaining what this drawer item is.
 * @param iconId The drawer items unique id number, used for finding and using the icon.
 * @param iconDescription The icons content description, in the item,
 * explaining what the icons is.
 */
@Composable
fun DrawerItem(
    itemText: String,
    selectedBoolean: Boolean,
    onClick: () -> Unit,
    itemDescription: String,
    iconId: Int = 0,
    iconDescription: String = ""
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = itemText,
                style = MaterialTheme.typography.titleSmall
            )
        },
        selected = selectedBoolean,
        onClick = { onClick() },
        modifier = Modifier.semantics {
            contentDescription = itemDescription
        },
        icon = {
            if(iconId != 0) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = iconDescription
                )
            }
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

/**
 * The dialog for filtering jokes.
 * @param openDialog The boolean state of if the filter jokes dialog is opened or not.
 * @param jokeFlagsList The list of joke flags(list of each joke flag content).
 * @param checkedFlagsState The boolean state of if the flag is checked or not.
 * @param checkedSafeFlagState The checked state for the safe mode checkbox.
 * @param enabledFlagCheckbox The enabled/disabled state
 * for checking/unchecking a flag checkbox.
 * @param loadJokeFlags The lambda block of loading the joke flags.
 * @param loadJokeList The lambda block of loading the joke list.
 * @param clearJokeList The lambda block of clearing the joke list.
 * @param addToCheckedFlagsList The lambda block of adding
 * a checked flag to checked flags list.
 * @param removeFromCheckedFlagsList The lambda block of removing
 * a checked flag from the checked flags list.
 * @param safeMode The mode for showing only safe jokes. The mode as a string/text.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterJokesDialog(
    openDialog: MutableState<Boolean>,
    jokeFlagsList: MutableState<List<JokeFlagsContent>>,
    checkedFlagsState: MutableMap<Int, MutableState<Boolean>>,
    checkedSafeFlagState: MutableState<Boolean>,
    enabledFlagCheckbox: MutableState<Boolean>,
    loadJokeFlags: () -> Unit = {},
    loadJokeList: () -> Unit = {},
    clearJokeList: () -> Unit = {},
    addToCheckedFlagsList: (JokeFlagsContent) -> Unit = {},
    removeFromCheckedFlagsList: (JokeFlagsContent) -> Unit = {},
    safeMode: MutableState<String?>
) {
    loadJokeFlags()
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when user clicks outside the dialog.
                // If you want to disable this functionality,
                // simply use empty onDismissRequest block.

                clearJokeList()
                loadJokeList()
                openDialog.value = false
            },
            modifier = Modifier
                .semantics { contentDescription = "Filter jokes dialog" }
        ) {
            Surface(
                modifier = Modifier
                    .semantics { contentDescription = "Filter jokes dialog surface" }
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.wrapContentWidth()) {
                    Text(
                        text = "Filter jokes",
                        modifier = Modifier
                            .semantics {
                                contentDescription = "Filter jokes title"
                            }
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 24.dp,
                                bottom = 16.dp
                            ),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    
                    Text(
                        text = "Check to filter, uncheck to unfilter",
                        modifier = Modifier
                            .semantics {
                                contentDescription = "Filter jokes dialog supporting text"
                            }
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                bottom = 24.dp
                            ),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        jokeFlagsList.value.forEach { jokeFlagsContent ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checkedFlagsState[jokeFlagsContent.jokeFlagId]!!.value,
                                    onCheckedChange = { checked ->
                                        checkedFlagsState[jokeFlagsContent.jokeFlagId]!!.value =
                                            checked
                                        if (checked == true) {
                                            addToCheckedFlagsList(jokeFlagsContent)
                                        } else {
                                            removeFromCheckedFlagsList(jokeFlagsContent)
                                        }
                                    },
                                    enabled = enabledFlagCheckbox.value
                                )

                                Text(
                                    text = jokeFlagsContent.jokeFlagName,
                                    modifier = Modifier
                                        .semantics {
                                            contentDescription = "Joke flag in filter list"
                                        },
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checkedSafeFlagState.value,
                                onCheckedChange = { checked ->
                                    checkedSafeFlagState.value = checked

                                    if (checked == true) {
                                        safeMode.value = "safe-mode"
                                        enabledFlagCheckbox.value = false
                                    } else {
                                        safeMode.value = null
                                        enabledFlagCheckbox.value = true
                                    }
                                }
                            )

                            Text(
                                text = "safe",
                                modifier = Modifier
                                    .semantics {
                                        contentDescription = "safe jokes text"
                                    },
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }

                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(
                            onClick = {
                                jokeFlagsList.value.forEach { jokeFlagsContent ->
                                    checkedFlagsState[jokeFlagsContent.jokeFlagId]!!.value = false
                                    checkedSafeFlagState.value = false
                                    removeFromCheckedFlagsList(jokeFlagsContent)
                                    enabledFlagCheckbox.value = true
                                    safeMode.value = null
                                }
                            },
                            modifier = Modifier
                                .semantics {
                                    contentDescription = "Unfilter all jokes text button"
                                },
                        ) {
                            Text(
                                text = "Unfilter all jokes",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        TextButton(
                            onClick = {
                                clearJokeList()
                                loadJokeList()
                                openDialog.value = false
                            },
                            modifier = Modifier
                                .semantics {
                                    contentDescription = "Back text button"
                                }
                        ) {
                            Text(
                                text = "Back",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Gets the flags list of checkable states.
 * @param flagsList The joke flags list.
 * @return The states into a Map.
 */
private fun getCheckableListStates(
    flagsList: List<JokeFlagsContent>
): MutableMap<Int, MutableState<Boolean>> {
    val states = mutableMapOf<Int, MutableState<Boolean>>()
    flagsList.forEach { jokeFlagsContent ->
        states[jokeFlagsContent.jokeFlagId] = mutableStateOf(jokeFlagsContent.filteredBoolean)
    }
    return states
}

/**
 * Initializes the states of checkable flags.
 * @param homeViewModel The ViewModel for the home screen.
 */
private fun initializeCheckableFlagStates(homeViewModel: HomeViewModel) {
    homeViewModel.jokeFlagsList.value.forEach { jokeFlagsContent ->
        homeViewModel.checkedFlagsState[jokeFlagsContent.jokeFlagId] = mutableStateOf(false)
    }
}

/**
 * If checkable flags states is empty, it gets and adds all the flag states.
 * Essentially filling the states of checked flags.
 * @param homeViewModel The ViewModel for the home screen.
 */
private fun putAllFlagStates(homeViewModel: HomeViewModel) {
    if (homeViewModel.checkedFlagsState.isEmpty()) {
        homeViewModel
            .checkedFlagsState
            .putAll(getCheckableListStates(homeViewModel.checkedFlagList))
    }
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
        checkedFlagsState = mutableMapOf(),
        checkedSafeFlagState = remember { mutableStateOf(false) },
        enabledFlagCheckbox = remember { mutableStateOf(false) },
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
        },
        openDialog = remember { mutableStateOf(false) },
        safeMode = remember { mutableStateOf(null) }
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
        checkedFlagsState = mutableMapOf(),
        checkedSafeFlagState = remember{ mutableStateOf(false) },
        enabledFlagCheckbox = remember { mutableStateOf(false) },
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
        },
        openDialog = remember { mutableStateOf(false) },
        safeMode = remember { mutableStateOf(null) }
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

/**
 * The "filter jokes" dialog and how it looks.
 */
@Composable
@Preview(showSystemUi = true)
fun FilterJokesDialogPreview() {
    FilterJokesDialog(
        openDialog = remember {mutableStateOf(true)},
        jokeFlagsList = remember {
            mutableStateOf(
                listOf(
                    JokeFlagsContent(jokeFlagId = 0, jokeFlagName = "Joke flag 1"),
                    JokeFlagsContent(jokeFlagId = 1, jokeFlagName = "Joke flag 2"),
                    JokeFlagsContent(jokeFlagId = 2, jokeFlagName = "Flag 3")
                )
            )
        },
        checkedFlagsState = remember {
            mutableMapOf(
                Pair(0, mutableStateOf(true)),
                Pair(1, mutableStateOf(false)),
                Pair(2, mutableStateOf(false))
            )
        },
        checkedSafeFlagState = remember { mutableStateOf(false) },
        enabledFlagCheckbox = remember { mutableStateOf(false) },
        safeMode = remember { mutableStateOf(null ) }
    )
}