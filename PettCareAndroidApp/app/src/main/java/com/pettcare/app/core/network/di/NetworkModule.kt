package com.pettcare.app.core.network.di

import com.pettcare.app.core.network.KtorClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun networkModule(baseUrl: String) = module {
    single<HttpClient> { KtorClient(baseUrl).client }
}
