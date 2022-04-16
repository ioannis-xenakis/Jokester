package com.john_xenakis.jokester.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.john_xenakis.jokester.animatedsplash.AnimatedSplashScreen
import com.john_xenakis.jokester.home.HomeScreen

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