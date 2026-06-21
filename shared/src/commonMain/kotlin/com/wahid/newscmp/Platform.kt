package com.wahid.newscmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform