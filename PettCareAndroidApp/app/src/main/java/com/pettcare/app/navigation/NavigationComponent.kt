package com.pettcare.app.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pettcare.app.bottomnav.BottomNavSelectedPublisher
import com.pettcare.app.create.presentation.PostType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject

@Composable
fun NavigationComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    HandleNavigation(navController = navController)
    NavHost(
        navController = navController,
        startDestination = NavigationDirections.Home.screen.destination,
        modifier = modifier,
    ) {
        val screenModifier = Modifier.fillMaxSize()
        composable(NavigationDirections.Welcome.screen.destination) {
            NavigationDirections.Welcome.screen.Screen(screenModifier, null)
        }

        composable(NavigationDirections.LogIn.screen.destination) {
            NavigationDirections.LogIn.screen.Screen(screenModifier, null)
        }

        composable(NavigationDirections.Registration.screen.destination) {
            NavigationDirections.Registration.screen.Screen(screenModifier, null)
        }
        composable(NavigationDirections.Home.screen.destination) {
            NavigationDirections.Home.screen.Screen(screenModifier, null)
        }
        composable(NavigationDirections.SocialWall.screen.destination) {
            NavigationDirections.SocialWall.screen.Screen(screenModifier, null)
        }
        composable(
            NavigationDirections.Profile.screen.destination,
            arguments = listOf(navArgument("userId") { type = NavType.StringType }),
        ) {
            NavigationDirections.Profile.screen.Screen(
                modifier = screenModifier,
                it.arguments?.getString("userId").orEmpty(),
            )
        }
        composable(NavigationDirections.ChooseWhatToCreate.screen.destination) {
            NavigationDirections.ChooseWhatToCreate.screen.Screen(screenModifier, null)
        }
        composable(
            route = NavigationDirections.CreatePost.screen.destination,
            arguments = listOf(navArgument("postTypeId") { type = NavType.StringType }),
        ) { backstackEntry ->
            NavigationDirections.CreatePost.screen.Screen(
                modifier = screenModifier,
                arguments = backstackEntry.arguments?.getString("postTypeId") ?: PostType.SOCIAL.id,
            )
        }
    }
}

@Composable
private fun HandleNavigation(
    navController: NavHostController,
    navActions: NavigationObserver = koinInject(),
) {
    navController.PublishCurrentScreen()
    LaunchedEffect(key1 = true) {
        navActions.observe().onEach { navAction ->
            navigationHandler(
                navController = navController,
                navAction = navAction,
            )
        }.launchIn(this)
    }
    BackHandler {
        navController.navigateUp()
    }
}

private fun navigationHandler(
    navController: NavHostController,
    navAction: NavigationAction,
) {
    when (navAction) {
        is NavigationAction.Navigate -> {
            val route = if (navAction.arguments.isNullOrBlank()) {
                navAction.navigationDestination.destination
            } else {
                navAction.navigationDestination.destination.split("/").first() + "/" + navAction.arguments
            }
            navController.navigate(route = route)
        }

        is NavigationAction.NavigateBack -> {
            navController.navigateUp()
        }

        is NavigationAction.PopUpTo -> {
            navController.navigate(
                route = navAction.navigationDestination.destination,
                popUpToDestination = navAction.popUpToDestination,
                isInclusive = navAction.inclusive,
            )
        }

        else -> {}
    }
}

private fun NavHostController.navigate(
    route: String,
    popUpToDestination: String? = null,
    isInclusive: Boolean = false,
) {
    navigate(route) {
        launchSingleTop = false
        popUpToDestination?.let {
            popUpTo(popUpToDestination) {
                inclusive = isInclusive
            }
        }
    }
}

@Composable
private fun NavHostController.PublishCurrentScreen() {
    val navItemPublisher: BottomNavSelectedPublisher = koinInject()

    LaunchedEffect(key1 = true) {
        currentBackStackEntryFlow.onEach { navBackStackEntry ->
            navBackStackEntry.destination.route?.let { route ->
                navItemPublisher.publishCurrentVisibleRoute(route)
            }
        }.launchIn(this)
    }
}
