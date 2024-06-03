package com.pettcare.app.navigation

internal interface NavigationPublisher {

    fun publish(destination: NavigationDestination, arguments: String?)

    fun popUpTo(destination: NavigationDestination, inclusive: Boolean = true, popUpToDestination: String)

    fun goBack()
}
