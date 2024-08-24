package com.pettcare.app.navigation

@Suppress("TooManyFunctions")
interface Router {

    fun welcomeScreen()

    fun loginScreen()

    fun signInScreen()

    fun goBack()

    fun home()

    fun socialWall()

    fun create()

    fun messages(userId: String)

    fun profile(profileId: String?)

    fun createPost(postTypeId: String?)

    fun userChats()
}
