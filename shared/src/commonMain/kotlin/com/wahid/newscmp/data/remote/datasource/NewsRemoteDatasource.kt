package com.wahid.newscmp.data.remote.datasource

import com.wahid.newscmp.data.remote.dto.NewsResponse

interface NewsRemoteDatasource {
    suspend fun getAllNews(query: Map<String, String>): NewsResponse
    suspend fun getHeadlinesNews(query: Map<String, String>): NewsResponse
}