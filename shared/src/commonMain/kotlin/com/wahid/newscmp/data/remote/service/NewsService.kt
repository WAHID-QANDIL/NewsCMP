package com.wahid.newscmp.data.remote.service

import com.wahid.newscmp.data.remote.dto.NewsResponse
import com.wahid.newscmp.utils.Result
import com.wahid.newscmp.utils.getQueryUrlString
import com.wahid.newscmp.utils.getResults
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.request.url

@Inject
class NewsService(private val httpClient: HttpClient) {

    suspend fun getAllNews(query: Map<String, String>): Result<NewsResponse> =
        httpClient.getResults<NewsResponse> {
            url("everything${query.getQueryUrlString()}")
        }


    suspend fun getHeadlinesNews(query: Map<String, String>): Result<NewsResponse> =
        httpClient.getResults<NewsResponse> {
            url("top-headlines${query.getQueryUrlString()}")
        }


}