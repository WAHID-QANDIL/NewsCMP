package com.wahid.newscmp.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Article(
    val id: String,
    val author: String,
    var isFavorite: Boolean,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val source: String,
    val url: String,
    val urlToImage: String
)