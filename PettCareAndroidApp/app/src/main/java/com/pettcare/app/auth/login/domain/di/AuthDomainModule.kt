package com.pettcare.app.auth.login.domain.di

import com.pettcare.app.auth.login.domain.usecase.LogInUser
import org.koin.dsl.module

val authDomainModule = module {
    factory { LogInUser(get()) }
}
