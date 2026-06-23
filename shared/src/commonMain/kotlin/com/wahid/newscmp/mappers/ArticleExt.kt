package com.wahid.newscmp.mappers

import com.wahid.newscmp.data.local.room.entity.ArticleEntity
import com.wahid.newscmp.domain.model.Article
import kotlin.time.Clock

fun com.wahid.newscmp.data.remote.dto.Article.toDomainModel(): Article {
    return Article(
        id = source?.id?:"NO ID",
        author = author ?: "No author info found",
        content = content ?: "No content",
        description = description ?: "No description",
        publishedAt = publishedAt ?: "No publishedAt info found",
        title = title ?: "No title",
        url = url ?: "",
        urlToImage = urlToImage?: "",
        isFavorite = false,
        source = source?.name ?: ""
    )
}
fun com.wahid.newscmp.data.remote.dto.Article.toDatabaseEntity( isFavorite: Boolean = false): ArticleEntity = ArticleEntity(
    id = source?.id?: "NO ID",
    isFavorite = isFavorite,
    author = author ?: "No author info found",
    content = content ?: "No content",
    description = description ?: "No description",
    publishedAt = publishedAt ?: "No publishedAt info found",
    title = title ?: "No title",
    url = url ?: "",
    urlToImage = urlToImage?: "",
    lastUpdate = Clock.System.now(),
    source = source?.name ?: ""
)

fun Article.toDatabaseModel(): ArticleEntity = ArticleEntity(
    id = id,
    isFavorite = isFavorite,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    source = source,
    url = url,
    urlToImage = urlToImage,
    lastUpdate = Clock.System.now()
)
fun ArticleEntity.toDomainModel(): Article = Article(
    id = id,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    isFavorite = isFavorite,
    source = source
)