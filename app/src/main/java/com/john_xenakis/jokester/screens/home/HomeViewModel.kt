package com.john_xenakis.jokester.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john_xenakis.jokester.data.models.JokeCategoryContent
import com.john_xenakis.jokester.data.models.JokeContent
import com.john_xenakis.jokester.data.models.JokeFlagsContent
import com.john_xenakis.jokester.repository.JokeRepository
import com.john_xenakis.jokester.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

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
 * The ViewModel for Home Screen,
 * that links the ui displaying part/view with the backend/model part.
 * @param jokeRepository The repository for getting jokes/calling requests to/from the api.
 * @return The ViewModel class.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jokeRepository: JokeRepository,
) : ViewModel() {

    /**
     * The list that contains all the jokes.
     */
    var jokeList = mutableStateOf<List<JokeContent>>(listOf())

    /**
     * The joke categories list that contains all the joke categories.
     */
    var jokeCategoryList = mutableStateOf<List<JokeCategoryContent>>(listOf())

    /**
     * The joke flags list that contains the joke flags.
     */
    var jokeFlagsList = mutableStateOf<List<JokeFlagsContent>>(listOf())

    /**
     * The list for checked(filtered) blacklist flags.
     */
    var checkedFlagList = mutableListOf<JokeFlagsContent>()

    /**
     * The error message text.
     */
    var loadError = mutableStateOf("")

    /**
     * The error message text for joke categories.
     */
    var loadJokeCategoryError = mutableStateOf("")

    /**
     * The error message text for joke flags.
     */
    var loadJokeFlagsError = mutableStateOf("")

    /**
     * The boolean for when the app is loading(for ex. loading/getting the jokes).
     */
    var isLoading = mutableStateOf(false)

    /**
     * The boolean for when the app loads the joke categories.
     */
    var isCategoriesLoading = mutableStateOf(false)

    /**
     * The boolean for when the app loads the joke flags.
     */
    var isJokeFlagsLoading = mutableStateOf(false)

    /**
     * The state of the flags, checked or not.
     */
    var checkedFlagsState = mutableMapOf<Int, MutableState<Boolean>>()

    /**
     * The mode for safe jokes as a string.
     */
    var safeMode = mutableStateOf<String?>(null)

    /**
     * The code number for the error type.
     */
    var errorCode = mutableStateOf(0)

    /**
     * The error code number for joke categories. Determines the error type.
     */
    var jokeCategoryErrorCode = mutableStateOf(0)

    /**
     * The error code number for joke flags when the api request fails.
     * Determines the error type.
     */
    var jokeFlagsErrorCode = mutableStateOf(0)

    /**
     * The function for loading the joke list.
     * @param jokeCategory The joke category that the jokes gets from.
     * @param flagList The blacklist flag list for filtering joke list.
     * @param safeMode The mode for filtering unsafe jokes
     * and showing only appropriate and safe jokes.
     */
    fun loadJokeList(
        jokeCategory: String = "Any",
        flagList: List<JokeFlagsContent>,
        safeMode: String? = null
    ) {
        viewModelScope.launch {
            Timber.d("loadJokeList started.")
            isLoading.value = true
            var flagsString = convertCheckedFlagsToStringWithCommas(flagList)
            when(
                val jokeListResult = jokeRepository.getJokeList(
                    jokeCategory = jokeCategory,
                    flags = flagsString,
                    safeMode = safeMode
            )) {
                is Resource.Success -> {
                    val jokeListItems = jokeListResult.data!!.jokes.mapIndexed { _, joke ->
                        val jokeText = if (joke.type == "twopart") joke.setup + "\n \n" + joke.delivery else joke.joke
                        JokeContent(jokeText!!)
                    }

                    loadError.value = ""
                    isLoading.value = false
                    jokeList.value += jokeListItems
                    Timber.d("Joke list size: ${jokeList.value.size}")
                    Timber.d("Joke list result is successful. \n \n")
                }
                is Resource.HttpError -> {
                    loadError.value = jokeListResult.message!!
                    errorCode.value = jokeListResult.code!!
                    isLoading.value = false
                    Timber.d("Http Error. Joke list not found.")
                }
                is Resource.NetworkError -> {
                    loadError.value = jokeListResult.message!!
                    errorCode.value = 0
                    isLoading.value = false
                    Timber.d("No internet connection. Joke list not found.")
                }
                is Resource.Error -> {
                    loadError.value = jokeListResult.message!!
                    errorCode.value = 0
                    isLoading.value = false
                    Timber.d("Error found. ${jokeListResult.message}")
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }

    /**
     * Loads the joke categories.
     */
    fun loadJokeCategories() {
        viewModelScope.launch {
            isCategoriesLoading.value = true
            when(val jokeCategoriesResult = jokeRepository.getJokeCategories()) {
                is Resource.Success -> {
                    val jokeCategoriesItems = jokeCategoriesResult
                        .data!!
                        .categories
                        .mapIndexed {
                                _, jokeCategory ->
                            JokeCategoryContent(jokeCategory)
                        }

                    loadJokeCategoryError.value = ""
                    isCategoriesLoading.value = false
                    jokeCategoryList.value = jokeCategoriesItems
                }
                is Resource.HttpError -> {
                    loadJokeCategoryError.value = jokeCategoriesResult.message!!
                    jokeCategoryErrorCode.value = jokeCategoriesResult.code!!
                    isCategoriesLoading.value = false
                }
                is Resource.NetworkError -> {
                    loadJokeCategoryError.value = jokeCategoriesResult.message!!
                    jokeCategoryErrorCode.value = 0
                    isCategoriesLoading.value = false
                }
                is Resource.Error -> {
                    loadJokeCategoryError.value = jokeCategoriesResult.message!!
                    jokeCategoryErrorCode.value = 0
                    isCategoriesLoading.value = false
                }
                is Resource.Loading -> {
                    isCategoriesLoading.value = true
                }
            }
        }
    }

    /**
     * Loads the joke flags.
     */
    fun loadJokeFlags() {
        viewModelScope.launch {
            isJokeFlagsLoading.value = true
            when(val jokeFlagsListResult = jokeRepository.getJokeFlagsList()) {
                is Resource.Success -> {
                    val jokeFlags = jokeFlagsListResult
                        .data!!
                        .flags
                        .mapIndexed { index, jokeFlag ->
                            JokeFlagsContent(jokeFlagId = index, jokeFlagName = jokeFlag)
                        }
                    loadJokeFlagsError.value = ""
                    isJokeFlagsLoading.value = false
                    jokeFlagsList.value = jokeFlags
                }
                is Resource.HttpError -> {
                    loadJokeFlagsError.value = jokeFlagsListResult.message!!
                    jokeFlagsErrorCode.value = jokeFlagsListResult.code!!
                    isJokeFlagsLoading.value = false
                }
                is Resource.NetworkError -> {
                    loadJokeFlagsError.value = jokeFlagsListResult.message!!
                    jokeFlagsErrorCode.value = 0
                    isJokeFlagsLoading.value = false
                }
                is Resource.Error -> {
                    loadJokeFlagsError.value = jokeFlagsListResult.message!!
                    jokeFlagsErrorCode.value = 0
                    isJokeFlagsLoading.value = false
                }
                is Resource.Loading -> {
                    isJokeFlagsLoading.value = true
                }
            }
        }
    }

    /**
     * Converts the checked flags, into a single string separated with commas.
     * @param checkedFlagsList The list of checked flags(list of JokeFlagsContent class).
     * @return The checked flags string separated with commas.
     */
    private fun convertCheckedFlagsToStringWithCommas(checkedFlagsList: List<JokeFlagsContent>): String? {
        var checkedFlagsString: String?
        if (checkedFlagsList.isNotEmpty()) {
            checkedFlagsString = ""
            checkedFlagsList.forEach { jokeFlagsContent ->
                checkedFlagsString += ",${jokeFlagsContent.jokeFlagName}"
            }
            checkedFlagsString = checkedFlagsString.removePrefix(",")
        } else {
            checkedFlagsString = null
        }

        return checkedFlagsString
    }

    /**
     * Adds the checked flags into the checked flags list.
     * @param jokeFlagsContent The content of a joke flag. Essentially the joke flag itself.
     */
    fun addToCheckedFlagsList(jokeFlagsContent: JokeFlagsContent) {
        checkedFlagList.add(jokeFlagsContent)
    }

    /**
     * Removes the checked flag from the checked flags list.
     * @param jokeFlagsContent The content of a joke flag. Essentially the joke flag itself.
     */
    fun removeFromCheckedFlagsList(jokeFlagsContent: JokeFlagsContent) {
        checkedFlagList.remove(jokeFlagsContent)
    }

    /**
     * Clears and empties the joke list.
     */
    fun clearJokeList() {
        jokeList.value = listOf()
    }

}