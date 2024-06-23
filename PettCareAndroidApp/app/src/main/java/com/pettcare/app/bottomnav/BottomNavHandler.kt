package com.pettcare.app.bottomnav

import com.pettcare.app.navigation.NavigationDirections
import com.pettcare.app.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy

internal class BottomNavHandler(
    private val router: Router,
) : BottomNavSelectedPublisher, BottomNavItemSelectedObserver {

    private val bottomNavItemPublisher = MutableStateFlow<BottomNavItem?>(null)

    override fun observe(): Flow<BottomNavItem?> = bottomNavItemPublisher.distinctUntilChangedBy {
        it?.name
    }

    override fun publish(item: BottomNavItem?) {
        item?.navToBottomNavStartDestination()
    }

    override fun publishCurrentVisibleRoute(route: String) {
        when (route) {
            NavigationDirections.Home.screen.destination -> {
                bottomNavItemPublisher.tryEmit(BottomNavItem.HOME)
            }

            else -> bottomNavItemPublisher.tryEmit(null)
        }
    }

    private fun BottomNavItem.navToBottomNavStartDestination() {
        when (this) {
            BottomNavItem.HOME -> router.home()
            BottomNavItem.SOCIAL_WALL -> router.socialWall()
            BottomNavItem.CREATE -> router.create()
            BottomNavItem.CHAT -> router.messages()
            BottomNavItem.PROFILE -> router.profile()
        }
    }
}
