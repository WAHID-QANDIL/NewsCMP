package com.wahid.newscmp.data.repository

import com.wahid.newscmp.data.local.datasource.NewsLocalDatasource
import com.wahid.newscmp.data.remote.datasource.NewsRemoteDatasource
import com.wahid.newscmp.di.IODispatcher
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.repository.NewsRepository
import com.wahid.newscmp.mappers.toDatabaseEntity
import com.wahid.newscmp.mappers.toDomainModel
import com.wahid.newscmp.utils.getOrThrow
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@ContributesBinding(AppScope::class)
@Inject
class NesRepositoryImpl(
    private val newsRemoteDatasource: NewsRemoteDatasource,
    private val newsLocalDatasource: NewsLocalDatasource,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {
    override fun getAllNews(queryFilter: Map<String, String>,forceFetch: Boolean): Flow<List<Article>> = flow {
        // The collector should flowOn(IO) dispatcher
        val articleEntities = newsLocalDatasource.getAllNews().first()
        if (!forceFetch){
            if (articleEntities.isNotEmpty()) {
                val lastUpdate = articleEntities[0].lastUpdate
                if (lastUpdate + CACHE_TIMEOUT > Clock.System.now()) {
                    emit(articleEntities.map { it.toDomainModel() })
                    return@flow
                }
            }
        }

        val articles = newsRemoteDatasource
            .getAllNews(query = queryFilter).getOrThrow()
            .articles
            ?.mapNotNull {
                it
            }?.filter { it.url?.isNotEmpty() == true }
            ?: run {
                emit(emptyList())
                return@flow
            }

        val existingFavorites = newsLocalDatasource.getAllNews().first()
            .filter { it.isFavorite }
            .map { it.id }
            .toSet()

        newsLocalDatasource.insert(
            articles.map { article ->
                article.toDatabaseEntity(
                    isFavorite = article.source?.id in existingFavorites
                )
            }
        )
        emit(articles.map { it.toDomainModel() })
    }.flowOn(coroutineDispatcher)

    /* This function is redundent, and should be removed.
    * I'll keep it for now and, will keep it in considerations to be removed, and replaced by getAllNews()'s same logic.
    *
    * UPDATE:
    * I changed my mind, this is completely different implementation, since we do not cache the headling articles
    */
    override fun getHeadLinesNews(queryFilter: Map<String, String>): Flow<List<Article>> = flow {
        val articles =
            newsRemoteDatasource.getHeadlinesNews(query = queryFilter).getOrThrow().articles?.mapNotNull { article ->
                article?.toDomainModel()
            }
        articles?.let {
            emit(it)
            return@flow
        }
        emit(emptyList())
    }.flowOn(coroutineDispatcher)

    companion object {
        private val CACHE_TIMEOUT: Duration = 30.minutes
    }
}