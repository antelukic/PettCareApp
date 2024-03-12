package com.pettcare.app.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pettcare.app.navigation.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<T>(
    val router: Router,
    initialViewState: T,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialViewState)
    val uiState = _uiState.asStateFlow()

    fun updateUiState(state: T) {
        viewModelScope.launch {
            _uiState.update {
                state
            }
        }
    }

    inline fun publishNavigationAction(block: (Router) -> Unit) {
        block(router)
    }

    protected fun goBack() = publishNavigationAction { router ->
        router.goBack()
    }

    inline fun launchInIO(crossinline block: suspend (CoroutineScope) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                block(this)
            }
        }
    }

    inline fun launchInMain(crossinline block: suspend (CoroutineScope) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                block(this)
            }
        }
    }
}
