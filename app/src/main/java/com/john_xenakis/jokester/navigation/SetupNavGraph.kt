package com.john_xenakis.jokester.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.john_xenakis.jokester.animatedsplash.AnimatedSplashScreen
import com.john_xenakis.jokester.home.HomeScreen

/**
 * This is where all screens are as composables,
 * setups the screens and their coding logic
 * and it's about how all screens should work.
 */
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) Screen.Splash.route else Screen.Home.route) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            composable(route = Screen.Splash.route) {
                AnimatedSplashScreen(navController = navController)
            }
        }
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }
}