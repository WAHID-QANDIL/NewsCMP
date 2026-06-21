package com.wahid.newscmp.data.remote

import com.wahid.newscmp.data.remote.dto.NewsResponse
import com.wahid.newscmp.di.AppGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject


@ContributesBinding(AppScope::class)
@Inject
class NewsRemoteDatasourceImpl(
    private val newsService: NewsService
): NewsRemoteDatasource {
    override suspend fun getAllNews(query: Map<String, String>): NewsResponse {
        return newsService.getAllNews(query = query)
    }

    override suspend fun getHeadlinesNews(query: Map<String, String>): NewsResponse {
        return newsService.getHeadlinesNews(query = query)
    }
}