package com.wahid.newscmp.di

import com.wahid.newscmp.data.local.datasource.NewsLocalDatasource
import com.wahid.newscmp.data.local.room.NewsDatabase
import com.wahid.newscmp.data.local.room.dao.NewsDao
import com.wahid.newscmp.data.remote.datasource.NewsRemoteDatasource
import com.wahid.newscmp.domain.repository.NewsRepository
import com.wahid.newscmp.domain.usecase.GetAllNewsUseCase
import com.wahid.newscmp.domain.usecase.GetHeadlinesUseCase
import com.wahid.newscmp.presentation.screen.allNews.AllNewsViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import io.ktor.client.HttpClient

@DependencyGraph(AppScope::class)
interface AppGraph: ViewModelGraph {
    val ktorClient: HttpClient
    val newsDatabase: NewsDatabase
    val newsDao: NewsDao
    val newsRemoteDatasource: NewsRemoteDatasource
    val newsLocalDatasource: NewsLocalDatasource
    val newsRepository: NewsRepository
    val getAllNewsUseCase: GetAllNewsUseCase
    val getHeadlinesUseCase: GetHeadlinesUseCase
    val allNewsViewModel: AllNewsViewModel

}