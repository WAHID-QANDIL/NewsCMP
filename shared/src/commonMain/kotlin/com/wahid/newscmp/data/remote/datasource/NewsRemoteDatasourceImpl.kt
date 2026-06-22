package com.wahid.newscmp.data.remote.datasource

import com.wahid.newscmp.data.remote.dto.NewsResponse
import com.wahid.newscmp.data.remote.service.NewsService
import com.wahid.newscmp.utils.Result
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject


@ContributesBinding(AppScope::class)
@Inject
class NewsRemoteDatasourceImpl(
    private val newsService: NewsService
): NewsRemoteDatasource {
    override suspend fun getAllNews(query: Map<String, String>): Result<NewsResponse> {
        return newsService.getAllNews(query = query)
    }

    override suspend fun getHeadlinesNews(query: Map<String, String>): Result<NewsResponse> {
        return newsService.getHeadlinesNews(query = query)
    }
}