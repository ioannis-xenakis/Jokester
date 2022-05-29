package com.john_xenakis.jokester.navigation

/**
 * All the screens are here.
 * No coding logic. Just the screens.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
sealed class Screen(val route: String) {
    /**
     * The splash screen(as an object).
     */
    object Splash : Screen("splash_screen")
    /**
     * The home screen(as an object).
     */
    object Home : Screen("home_screen")
}