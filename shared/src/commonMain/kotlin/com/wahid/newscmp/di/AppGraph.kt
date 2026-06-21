package com.wahid.newscmp.di

import com.wahid.newscmp.data.remote.NewsRemoteDatasource
import com.wahid.newscmp.domain.repository.NewsRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import io.ktor.client.HttpClient

@DependencyGraph(AppScope::class)
interface AppGraph{
    val ktorClient: HttpClient
    val newsRemoteDatasource: NewsRemoteDatasource
    val newsRepository: NewsRepository
}