package com.wahid.newscmp.domain.repository

import com.wahid.newscmp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getAllArticles(queryFilter: Map<String, String>, forceFetch: Boolean): Flow<List<Article>>
    fun getHeadLineArticles(queryFilter: Map<String, String>): Flow<List<Article>>


    fun getFavoriteArticles(): Flow<List<Article>>

    suspend fun addToFavorite(article: Article)
    suspend fun removeFromFavorite(article: Article)

}