package com.wahid.newscmp.di

import com.wahid.newscmp.utils.AuthPlugin
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


@ContributesTo(AppScope::class)
interface NetworkModule {

    @Provides
    fun providesKtorClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }

                level = LogLevel.BODY

                sanitizeHeader {
                    it == HttpHeaders.Authorization
                }
            }
            install(AuthPlugin)
            defaultRequest {
                url("https://newsapi.org/v2/")
            }
        }
    }
}