package com.john_xenakis.jokester.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john_xenakis.jokester.data.models.JokeContent
import com.john_xenakis.jokester.repository.JokeRepository
import com.john_xenakis.jokester.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * The ViewModel for Home Screen,
 * that links the ui displaying part/view with the backend/model part.
 * @param jokeRepository The repository for getting jokes/calling requests to/from the api.
 * @return The ViewModel class.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
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
     * The error message text.
     */
    var loadError = mutableStateOf("")

    /**
     * The boolean for when the app is loading(for ex. loading/getting the jokes).
     */
    var isLoading = mutableStateOf(false)

    /**
     * The code number for the error type.
     */
    var errorCode = mutableStateOf(0)

    /**
     * The function for loading the joke list.
     */
    fun loadJokeList() {
        viewModelScope.launch {
            Timber.d("loadJokeList started.")
            isLoading.value = true
            when(val jokeListResult = jokeRepository.getJokeList()) {
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
}