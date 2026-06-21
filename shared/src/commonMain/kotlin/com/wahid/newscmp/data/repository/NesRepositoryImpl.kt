package com.wahid.newscmp.data.repository

import com.wahid.newscmp.data.remote.NewsRemoteDatasource
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.repository.NewsRepository
import com.wahid.newscmp.mappers.toDomainModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ContributesBinding(AppScope::class)
@Inject
class NesRepositoryImpl(
    private val newsRemoteDatasource: NewsRemoteDatasource
) : NewsRepository {
    override fun getAllNews(queryFilter: Map<String, String>): Flow<List<Article>> = flow {
        val articles =
            newsRemoteDatasource.getAllNews(query = queryFilter).articles?.mapNotNull { article ->
                article?.toDomainModel()
            }
        articles?.let {
            emit(it)
            return@flow
        }
        emit(emptyList())
    }

    /* This function is redundent, and should be removed.
    * I'll keep it for now and, will keep it in considerations to be removed, and replaced by getAllNews()'s same logic.
    */
    override fun getHeadLinesNews(queryFilter: Map<String, String>): Flow<List<Article>> = flow {
        val articles =
            newsRemoteDatasource.getHeadlinesNews(query = queryFilter).articles?.mapNotNull { article ->
                article?.toDomainModel()
            }
        articles?.let {
            emit(it)
            return@flow
        }
        emit(emptyList())
    }
}