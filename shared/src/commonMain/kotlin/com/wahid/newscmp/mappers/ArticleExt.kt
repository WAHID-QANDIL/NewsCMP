package com.wahid.newscmp.mappers

import com.wahid.newscmp.domain.model.Article
import kotlin.time.Clock

fun com.wahid.newscmp.data.remote.dto.Article.getCacheKey(): String {
    source?.id?.let {
        return it
    }
    return "${hashCode() + Clock.System.now().nanosecondsOfSecond}"
}


fun com.wahid.newscmp.data.remote.dto.Article.toDomainModel(): Article {
    return Article(
        id = getCacheKey(),
        author = author ?: "No author info found",
        content = content ?: "No content",
        description = description ?: "No description",
        publishedAt = publishedAt ?: "No publishedAt info found",
        title = title ?: "No title",
        url = url ?: "",
        urlToImage = urlToImage?: ""
    )
}