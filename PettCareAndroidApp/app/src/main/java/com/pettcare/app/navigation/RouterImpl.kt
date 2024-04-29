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

    override fun home() {
        navigationPublisher.publish(NavigationDirections.Home.screen)
    }

    override fun socialWall() {
        navigationPublisher.publish(NavigationDirections.SocialWall.screen)
    }

    override fun create() {
        // no op
    }

    override fun messages() {
        // no op
    }

    override fun profile(profileId: String?) {
        // no op
    }
}
