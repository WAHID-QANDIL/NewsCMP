package com.wahid.newscmp.data.remote.service

import com.wahid.newscmp.data.remote.dto.NewsResponse
import com.wahid.newscmp.utils.getQueryUrlString
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

@Inject
class NewsService(private val httpClient: HttpClient) {

    suspend fun getAllNews(query: Map<String, String>): NewsResponse {
        val url = "everything${query.getQueryUrlString()}"
        return httpClient.get(urlString = url).body<NewsResponse>()
    }

    suspend fun getHeadlinesNews(query: Map<String, String>): NewsResponse {
        val url = "top-headlines${query.getQueryUrlString()}"
        return httpClient.get(urlString = url).body<NewsResponse>()
    }

}