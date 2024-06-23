package com.pettcare.app.bottomnav.di

import com.pettcare.app.bottomnav.BottomNavHandler
import com.pettcare.app.bottomnav.BottomNavItemSelectedObserver
import com.pettcare.app.bottomnav.BottomNavSelectedPublisher
import org.koin.dsl.binds
import org.koin.dsl.module

val bottomNavigationModule = module {
    single { BottomNavHandler(router = get()) } binds arrayOf(
        BottomNavItemSelectedObserver::class,
        BottomNavSelectedPublisher::class,
    )
}
