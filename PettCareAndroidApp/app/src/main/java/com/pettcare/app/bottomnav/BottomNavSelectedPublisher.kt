package com.pettcare.app.bottomnav

interface BottomNavSelectedPublisher {

    fun publishCurrentVisibleRoute(route: String)

    fun publish(item: BottomNavItem?)
}
