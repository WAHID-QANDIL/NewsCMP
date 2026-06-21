package com.wahid.newscmp.utils

fun Map<String, String>.getQueryUrlString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("?")
    entries.forEach { (key, value) ->
        stringBuilder.append(key)
        stringBuilder.append("=")
        stringBuilder.append(value)
        stringBuilder.append("&")
    }
    return stringBuilder.toString().substringBeforeLast("&")
}