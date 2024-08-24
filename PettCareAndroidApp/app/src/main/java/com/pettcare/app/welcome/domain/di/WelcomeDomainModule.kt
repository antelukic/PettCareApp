package com.pettcare.app.welcome.domain.di

import com.pettcare.app.welcome.domain.usecase.AuthenticateUser
import org.koin.dsl.module

val domainWelcomeModule = module {
    factory { AuthenticateUser(get()) }
}
