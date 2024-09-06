package com.pettcare.app.core.network

import android.annotation.SuppressLint
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.network.tls.TLSConfigBuilder
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class KtorClient {
    val client = HttpClient(CIO) {
        install(WebSockets)
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("HTTP", message)
                }
            }
            level = LogLevel.ALL
        }

        engine {
            https {
                trustManager = MyTrustManager(this)
            }
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                },
            )
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}

@SuppressLint("CustomX509TrustManager")
class MyTrustManager(private val config: TLSConfigBuilder) : X509TrustManager {
    private val delegate = config.build().trustManager

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(certificates: Array<out X509Certificate>?, authType: String?) {
        // No op
    }

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(certificates: Array<out X509Certificate>?, authType: String?) {
        // No op
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> = delegate.acceptedIssuers
}
