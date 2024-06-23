package com.pettcare.app.navigation

internal class RouterImpl(
    private val navigationPublisher: NavigationPublisher,
) : Router {

    override fun welcomeScreen() {
        navigationPublisher.publish(NavigationDirections.Welcome.screen, null)
    }

    override fun loginScreen() {
        navigationPublisher.publish(NavigationDirections.LogIn.screen, null)
    }

    override fun signInScreen() {
        navigationPublisher.publish(NavigationDirections.Registration.screen, null)
    }

    override fun goBack() {
        navigationPublisher.goBack()
    }

    override fun home() {
        navigationPublisher.publish(NavigationDirections.Home.screen, null)
    }

    override fun socialWall() {
        navigationPublisher.publish(NavigationDirections.SocialWall.screen, null)
    }

    override fun create() {
        navigationPublisher.publish(NavigationDirections.ChooseWhatToCreate.screen, null)
    }

    override fun messages() {
        navigationPublisher.publish(NavigationDirections.Chat.screen, null)
    }

    override fun profile(profileId: String?) {
        profileId?.let {
            navigationPublisher.publish(NavigationDirections.Profile.screen, profileId)
        }
    }

    override fun createPost(postTypeId: String?) {
        postTypeId?.let {
            navigationPublisher.publish(NavigationDirections.CreatePost.screen, postTypeId)
        }
    }
}
