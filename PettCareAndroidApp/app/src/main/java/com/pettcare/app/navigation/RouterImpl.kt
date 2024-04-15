package com.pettcare.app.navigation

internal class RouterImpl(
    private val navigationPublisher: NavigationPublisher,
) : Router {

    override fun welcomeScreen() {
        navigationPublisher.publish(NavigationDirections.Welcome.screen)
    }

    override fun loginScreen() {
        navigationPublisher.publish(NavigationDirections.LogIn.screen)
    }

    override fun signInScreen() {
        navigationPublisher.publish(NavigationDirections.Registration.screen)
    }

    override fun goBack() {
        navigationPublisher.goBack()
    }
}
