package com.wahid.newscmp.utils

import io.ktor.client.plugins.api.createClientPlugin

val AuthPlugin = createClientPlugin("AuthPlugin") {

    onRequest { request, _ ->
//        TODO("Remove hardcoded API Key ")
        request.url.parameters.append(
            "apiKey","API-KEY"
        )
    }
}