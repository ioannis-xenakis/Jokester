package com.john_xenakis.jokester

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * The ViewModel for the MainActivity
 * which is the unifier between the frontend(the view and the visual part) and the backend(the model).
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
class MainViewModel: ViewModel() {
    /**
     * The private variable for setting if splash screen is still loading.
     */
    private val _isLoading = MutableStateFlow(true)

    /**
     * The variable for setting if splash screen is still loading.
     */
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(30L)
            _isLoading.value = false
        }
    }
}