package com.john_xenakis.jokester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.john_xenakis.jokester.navigation.SetupNavGraph
import com.john_xenakis.jokester.ui.theme.JokesterTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The Main Activity which contains the screens(composables).
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * The ViewModel for the MainActivity
     * which unifies the visual part(the view), with the backend(the model).
     */
    private val viewModel: MainViewModel by viewModels()

    /**
     * The function which gets called when the Activity first starts/is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokesterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    val isDarkTheme = isSystemInDarkTheme()
                    val backgroundColor = MaterialTheme.colorScheme.background

                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = backgroundColor,
                            darkIcons = !isDarkTheme
                        )
                    }
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}