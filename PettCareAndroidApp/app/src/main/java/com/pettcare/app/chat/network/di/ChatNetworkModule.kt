package com.pettcare.app.chat.network.di

import android.util.Log
import com.pettcare.app.chat.network.ChatService
import com.pettcare.app.chat.network.ChatServiceImpl
import com.pettcare.app.chat.network.ChatSocketService
import com.pettcare.app.chat.network.ChatSocketServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
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

            install(JsonFeature) {
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            }
        }
    }

    single<ChatSocketService> { ChatSocketServiceImpl() }
    single<ChatService> { ChatServiceImpl(get(), get()) }
}
