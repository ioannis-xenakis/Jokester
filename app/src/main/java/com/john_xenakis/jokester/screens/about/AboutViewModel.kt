package com.john_xenakis.jokester.screens.about

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import john_xenakis.jokester.BuildConfig
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
 * The ViewModel for About Screen,
 * that links the ui displaying part/view with the backend/model part.
 * @return The ViewModel class.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
class AboutViewModel : ViewModel() {
    /**
     * Directs user at the google play store page of this app.
     * @param context The context of the page/activity.
     */
    fun goToStorePage(context: Context) {
        viewModelScope.launch {
            try {
                Timber.d("Opening google play store app.")
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                    )

                    setPackage("com.android.vending")
                }

                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Timber.d("Google play store app not found. Opening with a web browser.")
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                    )
                }

                context.startActivity(intent)
            }
        }
    }

    /**
     * Directs user at the github repo page of this app.
     * @param context The context of the current screen(about screen)/activity(main activity)
     */
    fun goToGithubRepoPage(context: Context) {
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://github.com/ioannis-xenakis/Jokester/tree/Develop"
                )
            }

            context.startActivity(intent)
        }
    }

    /**
     * Directs user to open a mail app of his/her choice,
     * to send a mail to the developer of this app.
     * @param context The context of the current screen/activity.
     */
    fun mailToDeveloper(context: Context) {
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"

                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf("xenakis.i.contact@gmail.com")
                )
            }

            context.startActivity(intent)
        }
    }
}