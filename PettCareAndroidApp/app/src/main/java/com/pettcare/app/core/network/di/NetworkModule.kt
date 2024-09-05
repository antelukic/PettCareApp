package com.pettcare.app.core.network.di

import com.pettcare.app.core.network.KtorClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun networkModule() = module {
    single<HttpClient> { KtorClient().client }
}
