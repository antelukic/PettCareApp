package com.pettcare.app.bottomnav

import kotlinx.coroutines.flow.Flow

interface BottomNavItemSelectedObserver {

    fun observe(): Flow<BottomNavItem?>
}
