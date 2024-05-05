package com.pettcare.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pettcare.app.auth.login.presentation.LogInScreen
import com.pettcare.app.auth.signin.presentation.SignInScreen
import com.pettcare.app.home.presentation.HomeScreen
import com.pettcare.app.profile.presentation.ProfileScreen
import com.pettcare.app.socialwall.presentation.SocialWallScreen
import com.pettcare.app.welcome.WelcomeScreen

sealed class NavigationDirections {
    object Welcome {
        val screen = object : NavigationDestination {
            override val destination: String = "welcome"

            @Composable
            override fun Screen(modifier: Modifier) {
                WelcomeScreen(modifier)
            }
        }
    }

    object LogIn {
        val screen = object : NavigationDestination {
            override val destination: String = "login"

            @Composable
            override fun Screen(modifier: Modifier) {
                LogInScreen(modifier)
            }
        }
    }

    object Registration {
        val screen = object : NavigationDestination {
            override val destination: String = "registration"

            @Composable
            override fun Screen(modifier: Modifier) {
                SignInScreen(modifier)
            }
        }
    }

    object Home {
        val screen = object : NavigationDestination {
            override val destination: String = "home"

            @Composable
            override fun Screen(modifier: Modifier) {
                HomeScreen(modifier)
            }
        }
    }

    object SocialWall {
        val screen = object : NavigationDestination {
            override val destination: String = "socialWall"

            @Composable
            override fun Screen(modifier: Modifier) {
                SocialWallScreen(modifier)
            }
        }
    }

    object Profile {
        fun screen(argument: String) = object : NavigationDestination {
            override val destination: String = "profile/{userId}"

            @Composable
            override fun Screen(modifier: Modifier) {
                ProfileScreen(id = argument)
            }
        }
    }
}
