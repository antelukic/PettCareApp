package com.pettcare.app.navigation

import kotlinx.coroutines.flow.Flow

internal interface NavigationObserver {

    fun observe(): Flow<NavigationAction>
}
