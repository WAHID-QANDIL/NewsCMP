package com.wahid.newscmp.data.local.room.entity

import androidx.room.Entity
import kotlin.time.Instant

@Entity(tableName = "Article",primaryKeys = ["id"])
data class ArticleEntity(
    val id: String,
    val isFavorite: Boolean,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val lastUpdate: Instant
)