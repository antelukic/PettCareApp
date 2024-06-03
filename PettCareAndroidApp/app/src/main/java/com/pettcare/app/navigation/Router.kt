package com.pettcare.app.navigation

interface Router {

    fun welcomeScreen()

    fun loginScreen()

    fun signInScreen()

    fun goBack()

    fun home()

    fun socialWall()

    fun create()

    fun messages()

    fun profile(profileId: String?)

    fun createPost(postTypeId: String?)
}
