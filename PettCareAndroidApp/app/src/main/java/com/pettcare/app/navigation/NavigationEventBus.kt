package com.pettcare.app.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class NavigationEventBus : NavigationObserver, NavigationPublisher {

    private val navigationState = MutableSharedFlow<NavigationAction>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override fun goBack() {
        navigationState.tryEmit(NavigationAction.NavigateBack)
    }

    override fun observe(): Flow<NavigationAction> = navigationState

    override fun publish(destination: NavigationDestination) {
        navigationState.tryEmit(NavigationAction.Navigate(destination))
    }

    override fun popUpTo(destination: NavigationDestination, inclusive: Boolean, popUpToDestination: String) {
        navigationState.tryEmit(NavigationAction.PopUpTo(destination, popUpToDestination, inclusive))
    }
}
