package com.pettcare.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import com.pettcare.app.auth.login.LoginScreen
import com.pettcare.app.auth.signin.SignInScreen
import com.pettcare.app.welcome.WelcomeScreen

object NavigationDirections {

    object Welcome {
        val screen = object : NavigationDestination {
            override val arguments: List<NamedNavArgument> = emptyList()
            override val destination: String = "welcome"

            @Composable
            override fun Screen(modifier: Modifier) {
                WelcomeScreen(modifier)
            }
        }
    }

    object LogIn {
        val screen = object : NavigationDestination {
            override val arguments: List<NamedNavArgument> = emptyList()
            override val destination: String = "login"

            @Composable
            override fun Screen(modifier: Modifier) {
                LoginScreen(modifier)
            }
        }
    }

    object SignIn {
        val screen = object : NavigationDestination {
            override val arguments: List<NamedNavArgument> = emptyList()
            override val destination: String = "signin"

            @Composable
            override fun Screen(modifier: Modifier) {
                SignInScreen(modifier)
            }
        }
    }
}
