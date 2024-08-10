package com.pettcare.app.auth.signin.domain.di

import com.pettcare.app.auth.signin.domain.GetDateFromMillis
import com.pettcare.app.auth.signin.domain.SignInUser
import org.koin.dsl.module

val signInDomainModule = module {
    factory { GetDateFromMillis() }
    single { SignInUser(get()) }
}
