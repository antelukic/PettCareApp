package com.pettcare.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import com.pettcare.app.auth.login.presentation.LogInScreen
import com.pettcare.app.auth.signin.presentation.SignInScreen
import com.pettcare.app.home.presentation.HomeScreen
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
                LogInScreen(modifier)
            }
        }
    }

    object Registration {
        val screen = object : NavigationDestination {
            override val arguments: List<NamedNavArgument> = emptyList()
            override val destination: String = "registration"

            @Composable
            override fun Screen(modifier: Modifier) {
                SignInScreen(modifier)
            }
        }
    }

    object Home {
        val screen = object : NavigationDestination {
            override val arguments: List<NamedNavArgument> = emptyList()
            override val destination: String = "home"

            @Composable
            override fun Screen(modifier: Modifier) {
                HomeScreen(modifier)
            }
        }
    }
}
