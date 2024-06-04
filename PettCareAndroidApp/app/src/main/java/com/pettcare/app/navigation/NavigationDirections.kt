package com.pettcare.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pettcare.app.auth.login.presentation.LogInScreen
import com.pettcare.app.auth.signin.presentation.SignInScreen
import com.pettcare.app.chat.presentation.ChatScreen
import com.pettcare.app.create.presentation.PostType
import com.pettcare.app.create.presentation.choosewhattocreate.ChooseWhatToCreateScreen
import com.pettcare.app.create.presentation.createpost.CreatePostScreen
import com.pettcare.app.home.presentation.HomeScreen
import com.pettcare.app.profile.presentation.ProfileScreen
import com.pettcare.app.socialwall.presentation.SocialWallScreen
import com.pettcare.app.welcome.WelcomeScreen

sealed class NavigationDirections {
    object Welcome {
        val screen = object : NavigationDestination {
            override val destination: String = "welcome"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                WelcomeScreen(modifier)
            }
        }
    }

    object LogIn {
        val screen = object : NavigationDestination {
            override val destination: String = "login"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                LogInScreen(modifier)
            }
        }
    }

    object Registration {
        val screen = object : NavigationDestination {
            override val destination: String = "registration"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                SignInScreen(modifier)
            }
        }
    }

    object Home {
        val screen = object : NavigationDestination {
            override val destination: String = "home"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                HomeScreen(modifier)
            }
        }
    }

    object SocialWall {
        val screen = object : NavigationDestination {
            override val destination: String = "socialWall"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                SocialWallScreen(modifier)
            }
        }
    }

    object Profile {
        val screen = object : NavigationDestination {
            override val destination: String = "profile/{userId}"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                ProfileScreen(id = (arguments as? String).orEmpty(), modifier = modifier)
            }
        }
    }

    object ChooseWhatToCreate {
        val screen = object : NavigationDestination {
            override val destination: String = "chooseWhatToCreate"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                ChooseWhatToCreateScreen(modifier)
            }
        }
    }

    object CreatePost {
        val screen = object : NavigationDestination {
            override val destination: String = "create/{postTypeId}"

            @Composable
            override fun Screen(
                modifier: Modifier,
                arguments: Any?,
            ) {
                CreatePostScreen(PostType.fromId(arguments as? String), modifier)
            }
        }
    }

    object Chat {
        val screen = object : NavigationDestination {
            override val destination: String = "chat"

            @Composable
            override fun Screen(modifier: Modifier, arguments: Any?) {
                ChatScreen(modifier)
            }
        }
    }
}
