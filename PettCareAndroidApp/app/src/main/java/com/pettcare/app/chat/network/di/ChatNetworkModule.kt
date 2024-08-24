package com.pettcare.app.chat.network.di

import android.util.Log
import com.pettcare.app.chat.network.ChatService
import com.pettcare.app.chat.network.ChatServiceImpl
import com.pettcare.app.chat.network.ChatSocketService
import com.pettcare.app.chat.network.ChatSocketServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val chatNetworkModule = module {
    single<HttpClient> {
        HttpClient(CIO) {

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                    },
                )
            }
        }
    }

    factory<ChatSocketService> { ChatSocketServiceImpl(get(), get()) }
    factory<ChatService> { ChatServiceImpl(get(), get()) }
}
