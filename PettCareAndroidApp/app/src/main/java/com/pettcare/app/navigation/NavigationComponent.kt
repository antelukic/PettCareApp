package com.pettcare.app.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            NavigationDirections.Welcome.screen.Screen(screenModifier)
        }

        composable(NavigationDirections.LogIn.screen.destination) {
            NavigationDirections.LogIn.screen.Screen(screenModifier)
        }

        composable(NavigationDirections.Registration.screen.destination) {
            NavigationDirections.Registration.screen.Screen(screenModifier)
        }
        composable(NavigationDirections.Home.screen.destination) {
            NavigationDirections.Home.screen.Screen(screenModifier)
        }
    }
}

@Composable
private fun HandleNavigation(
    navController: NavHostController,
    navActions: NavigationObserver = koinInject(),
) {
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
            navController.navigate(navAction.navigationDestination.destination)
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
