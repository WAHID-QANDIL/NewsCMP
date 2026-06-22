package com.wahid.newscmp.mappers

import com.wahid.newscmp.data.local.room.entity.ArticleEntity
import com.wahid.newscmp.domain.model.Article
import kotlin.time.Clock

/*fun com.wahid.newscmp.data.remote.dto.Article.getCacheKey(): String {
    source?.id?.let {
        return it
    }
    return "${hashCode() + Clock.System.now().nanosecondsOfSecond}"
}*/


fun com.wahid.newscmp.data.remote.dto.Article.toDomainModel(): Article {
    return Article(
        id = source?.id?:"",
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
    id = source?.id?: "",
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