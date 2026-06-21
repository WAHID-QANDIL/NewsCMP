package com.wahid.newscmp.domain.repository

import com.wahid.newscmp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getAllNews(queryFilter: Map<String, String>,forceFetch: Boolean): Flow<List<Article>>
    fun getHeadLinesNews(queryFilter: Map<String, String>): Flow<List<Article>>

}