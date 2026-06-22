package com.wahid.newscmp.data.remote.datasource

import com.wahid.newscmp.data.remote.dto.NewsResponse
import com.wahid.newscmp.utils.Result

interface NewsRemoteDatasource {
    suspend fun getAllNews(query: Map<String, String>): Result<NewsResponse>
    suspend fun getHeadlinesNews(query: Map<String, String>): Result<NewsResponse>
}