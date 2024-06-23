package com.pettcare.app.navigation.di

import com.pettcare.app.navigation.NavigationEventBus
import com.pettcare.app.navigation.NavigationObserver
import com.pettcare.app.navigation.NavigationPublisher
import com.pettcare.app.navigation.Router
import com.pettcare.app.navigation.RouterImpl
import org.koin.dsl.binds
import org.koin.dsl.module

val navigationModule = module {
    single { NavigationEventBus() } binds arrayOf(NavigationPublisher::class, NavigationObserver::class)
    single<Router> { RouterImpl(get()) }
}
