package com.john_xenakis.jokester.screens.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import john_xenakis.jokester.BuildConfig
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
 * The about screen.
 * @param aboutViewModel The ViewModel for the about screen.
 * @param navController The controller for navigating from screen to screen,
 * or responsible for anything related to navigation.
 */
@Composable
fun AboutScreen(
    aboutViewModel: AboutViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    AboutUI(
        aboutUIEvents = Events(
            backToHomeScreen = { navController.popBackStack() },
            goToStorePage = { aboutViewModel.goToStorePage(context) },
            goToGithubRepoPage = { aboutViewModel.goToGithubRepoPage(context) },
            mailToDeveloper = { aboutViewModel.mailToDeveloper(context) }
        ),
        appVersion = BuildConfig.VERSION_NAME,
        isSnackbarShowing = aboutViewModel.isSnackbarShowing
    )
}

/**
 * The content/ui of about screen.
 * @param appVersion The app version into a text.
 * @param openWhatsNewDialog The boolean for choosing
 * if the "whats new" dialog will be open or closed.
 * @param aboutUIEvents The events(for ex. onClick events).
 * @param isSnackbarShowing The boolean, deciding if the snackbar should be shown or not.
 */
@Composable
fun AboutUI(
    appVersion: String = "",
    openWhatsNewDialog: MutableState<Boolean> = mutableStateOf(false),
    aboutUIEvents: Events = Events(),
    isSnackbarShowing: MutableState<Boolean> = mutableStateOf(false)
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBarAboutScreen(aboutUIEvents)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        MainContentAboutScreen(
            padding = padding,
            appVersion = appVersion,
            openWhatsNewDialog = openWhatsNewDialog,
            aboutScreenEvents = aboutUIEvents
        )

        WhatsNewDialog(openWhatsNewDialog, padding)

        Snackbar(
            scope = scope,
            isSnackbarShowing = isSnackbarShowing,
            snackbarHostState = snackbarHostState
        )
    }
}

/**
 * The top app bar for about screen.
 * @param topBarEvents The functions/blocks for the events
 * (for ex. using a function for an onClick event).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAboutScreen(topBarEvents: Events = Events()) {
    TopAppBar(
        title = {
            Text(
                text = "About Jokester",
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = Modifier.semantics { contentDescription = "Top app bar" },
        navigationIcon = {
            IconButton(onClick = { topBarEvents.backToHomeScreen() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_nav_icon),
                    contentDescription = "Icon for navigation button",
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
 * The snackbar of "about" screen.
 * @param scope The coroutine scope.
 * @param isSnackbarShowing The boolean deciding if the snackbar should open or not.
 * @param snackbarHostState The snackbar and its state, controlling what snackbar should do.
 */
@Composable
fun Snackbar(
    scope: CoroutineScope = rememberCoroutineScope(),
    isSnackbarShowing: MutableState<Boolean> = mutableStateOf(true),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Timber.d("isSnackbarShowing boolean: " + isSnackbarShowing.value)

    if (isSnackbarShowing.value) {
        LaunchedEffect(isSnackbarShowing) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "Didn't find an app to open Github." +
                            " Please install an app to use."
                )

                isSnackbarShowing.value = false
            }
        }
    }
}

/**
 * The main content of about screen.
 * @param padding Padding of main content in about screen.
 * @param appVersion The app version in a string.
 * @param openWhatsNewDialog Decides if "whats new" dialog is open or not.
 * @param aboutScreenEvents The functions/blocks for the events
 * (for ex. using a function for an onClick event).
 */
@Composable
fun MainContentAboutScreen(
    padding: PaddingValues,
    appVersion: String,
    openWhatsNewDialog: MutableState<Boolean> = mutableStateOf(false),
    aboutScreenEvents: Events
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Thank you for trying this app! I hope you have a good laugh!",
                modifier = Modifier.padding(
                    start = 56.dp,
                    top = 10.dp,
                    end = 16.dp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge
            )

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 23.dp),
                horizontalAlignment = Alignment.Start
            ) {
                ListButton(
                    texts = Texts(
                        textOne = "What's New",
                        textTwo = appVersion
                    ),
                    icon = Icons(
                        iconContentDescription = "Icon for whats new button",
                        iconId = R.drawable.exclamation_mark_icon
                    ),
                    buttonStatesEvents = ButtonStatesEvents(
                        contentDescription = "Whats new button",
                        onClick = { openWhatsNewDialog.value = true }
                    )
                )

                DividerLine("Divider line below whats new button")

                ListButton(
                    texts = Texts(textOne = "Rate Jokester"),
                    icon = Icons(
                        iconId = R.drawable.star_icon,
                        iconContentDescription = "Icon for rate jokester button"
                    ),
                    buttonStatesEvents = ButtonStatesEvents(
                        contentDescription = "Rate jokester text button",
                        onClick = aboutScreenEvents.goToStorePage
                    )
                )

                DividerLine("Divider line below rate jokester button")
                
                ListButton(
                    texts = Texts(
                        textOne = "Source Code",
                        textTwo = "Jokester is an open-source free app licensed" +
                                " under the GNU General Public License v3.0"
                    ),
                    icon = Icons(
                        iconContentDescription = "Icon on source code button",
                        iconId = R.drawable.github_monster_icon
                    ),
                    buttonStatesEvents = ButtonStatesEvents(
                        contentDescription = "Source code button",
                        onClick = aboutScreenEvents.goToGithubRepoPage
                    )
                )

                DividerLine(contentDescriptionDivider = "Divider below source code button")

                ListButton(
                    texts = Texts(textOne = "Contact Developer"),
                    icon = Icons(
                        iconContentDescription = "Icon on contact developer button",
                        iconId = R.drawable.mail_folder_icon
                    ),
                    buttonStatesEvents = ButtonStatesEvents(
                        contentDescription = "Contact developer button",
                        onClick = aboutScreenEvents.mailToDeveloper
                    )
                )
            }
        }
    }
}

/**
 * Divider line for list buttons.
 * @param contentDescriptionDivider The content description for the divider.
 */
@Composable
fun DividerLine(contentDescriptionDivider: String) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = contentDescriptionDivider
            },
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

/**
 * This creates a list button.
 * @param texts The texts of the about screen.
 * @param icon The icons of the about screen.
 * @param buttonStatesEvents The states and events of a list button.
 */
@Composable
fun ListButton(
    texts: Texts,
    icon: Icons = Icons(),
    buttonStatesEvents: ButtonStatesEvents = ButtonStatesEvents()
) {
    Box(
        modifier = Modifier
            .semantics { contentDescription = buttonStatesEvents.contentDescription }
            .clickable { buttonStatesEvents.onClick() }
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon.iconId != 0) {
                Icon(
                    painter = painterResource(id = icon.iconId),
                    contentDescription = icon.iconContentDescription,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Column {
                Text(
                    text = texts.textOne,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )

                if (texts.textTwo != "") {
                    Text(
                        text = texts.textTwo,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

/**
 * Texts for usage in list buttons.
 * @param textOne The first text
 * @param textTwo The second text
 */
data class Texts(
    val textOne: String = "",
    val textTwo: String = ""
)

/**
 * Icons for usage in list buttons.
 * @param iconContentDescription The content description,
 * describing what the icon is and it is also used in tests.
 * @param iconId The id number of the icon,
 * identifying and finding the right icon.
 * Each id number is unique to each icon.
 */
data class Icons(
    val iconContentDescription: String = "",
    val iconId: Int = 0
)

/**
 * The functions for events(for ex. onClick events).
 * @param backToHomeScreen The block for sending user, back to Home Screen.
 * @param goToStorePage The block for sending user, to app's Google Play Store page.
 * @param goToGithubRepoPage The block for sending user, to app's Github Repository page.
 * @param mailToDeveloper The block for the user to open a mail app of his choice
 * and send a mail to the developer of this app.
 */
data class Events(
    val backToHomeScreen: () -> Unit = {},
    val goToStorePage: () -> Unit = {},
    val goToGithubRepoPage: () -> Unit = {},
    val mailToDeveloper: () -> Unit = {}
)

/**
 * List buttons states and events.
 * @param contentDescription The content description for a list button.
 * Content description is a text that describes what this button is and also is used for testing usage.
 * @param onClick The clicking event for a list button. When button is clicked, this is being ran.
 */
data class ButtonStatesEvents(
    val contentDescription: String = "",
    val onClick: () -> Unit = {}
)

/**
 * The back button, on "whats new" dialog.
 * @param openDialog The boolean state
 * for closing the "whats new" dialog(boolean is "false").
 */
@Composable
fun BackButton(openDialog: MutableState<Boolean>) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(top = 24.dp)) {
        TextButton(
            onClick = {
                openDialog.value = false
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .semantics {
                    contentDescription = "Back text button"
                },
        ) {
            Text(
                text = "Back",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

/**
 * Dialog for showing what is new for each Jokester version.
 * @param openDialog The boolean state of
 * if the "whats new" dialog is opened or not.
 * @param mainContentPadding Padding from main content of about screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsNewDialog(
    openDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    },
    mainContentPadding: PaddingValues = PaddingValues()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(mainContentPadding),
        contentAlignment = Alignment.Center
    ) {
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when user clicks outside the dialog.
                    // If you want to disable this functionality,
                    // simply use empty onDismissRequest block.

                    openDialog.value = false
                },
                modifier = Modifier.semantics { contentDescription = "Whats new dialog" }
            ) {
                Surface(
                    modifier = Modifier
                        .semantics { contentDescription = "Whats new dialog surface" }
                        .wrapContentSize(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        WhatsNewMainInfo()
                        BackButton(openDialog = openDialog)
                    }
                }
            }
        }
    }
}

/**
 * The main text and info inside "what's new" dialog.
 */
@Composable
fun WhatsNewMainInfo() {
    Column(
        modifier = Modifier.padding(bottom = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Text(
                text = "What's New:",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .wrapContentWidth()
            )

            Text(
                text = BuildConfig.VERSION_NAME + "" +
                        " (" + BuildConfig.APP_VERSION_DATE + "):",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = "NEW:\n" +
                        "• About Screen.\n" +
                        "    • Whats new button with dialog. \n" +
                        "    • Rate Jokester button. \n" +
                        "    • Source code button. \n" +
                        "    • Contact developer button. \n" +
                        "• Filter jokes with tags, from home screen.\n" +
                        "    • Safe jokes tag. \n" +
                        "    • Other tags. \n" +
                        "\n" +
                        "REMOVE:\n" +
                        "• \"Support me\" button, in side navigation drawer, from home screen.",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Jokester. \n" +
                    "All Rights Reserved.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/**
 * The function for previewing about screen on the IDE(Android studio).
 */
@Preview(showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    AboutUI(appVersion = BuildConfig.VERSION_NAME)
}

/**
 * The function for previewing on IDE(Android Studio), only the "whats new" dialog.
 */
@Preview(showSystemUi = true)
@Composable
fun WhatsNewDialogPreview() {
    WhatsNewDialog(openDialog = remember { mutableStateOf(true) })
}