package com.pettcare.app.navigation

internal sealed class NavigationAction {

    @Deprecated("Use only as initial action")
    data object Default : NavigationAction()

    data class Navigate(val navigationDestination: NavigationDestination) : NavigationAction()

    data class PopUpTo(
        val navigationDestination: NavigationDestination,
        val popUpToDestination: String,
        val inclusive: Boolean,
    ) : NavigationAction()

    data object NavigateBack : NavigationAction()
}
