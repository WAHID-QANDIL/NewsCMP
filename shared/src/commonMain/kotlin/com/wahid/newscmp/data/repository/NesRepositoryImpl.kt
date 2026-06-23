package com.wahid.newscmp.data.repository

import com.wahid.newscmp.data.local.datasource.NewsLocalDatasource
import com.wahid.newscmp.data.remote.datasource.NewsRemoteDatasource
import com.wahid.newscmp.di.IODispatcher
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.repository.NewsRepository
import com.wahid.newscmp.mappers.toDatabaseEntity
import com.wahid.newscmp.mappers.toDatabaseModel
import com.wahid.newscmp.mappers.toDomainModel
import com.wahid.newscmp.utils.getOrThrow
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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
    val coroutineScope = CoroutineScope(coroutineDispatcher)


    override fun getAllArticles(
        queryFilter: Map<String, String>,
        forceFetch: Boolean
    ): Flow<List<Article>> =
        newsLocalDatasource
            .getAllNews()
            .map { entities ->
                entities.map { it.toDomainModel() }
            }
            .onStart {
                val cachedArticles =
                    newsLocalDatasource.getAllNews().first()

                val shouldFetch =
                    forceFetch ||
                            cachedArticles.isEmpty() ||
                            cachedArticles.first().lastUpdate + CACHE_TIMEOUT <= Clock.System.now()

                if (shouldFetch) {

                    val remoteArticles =
                        newsRemoteDatasource
                            .getAllNews(queryFilter)
                            .getOrThrow()
                            .articles
                            ?.filterNotNull()
                            ?.filter { !it.url.isNullOrBlank() }
                            ?: emptyList()

                    val favorites =
                        cachedArticles
                            .filter { it.isFavorite }
                            .mapNotNull { it.url }
                            .toSet()

                    newsLocalDatasource.insert(
                        remoteArticles.map {
                            it.toDatabaseEntity(
                                isFavorite = it.url in favorites
                            )
                        }
                    )
                }
            }
            .flowOn(coroutineDispatcher)


    override fun getHeadLineArticles(queryFilter: Map<String, String>): Flow<List<Article>> = flow {
        val articles =
            newsRemoteDatasource.getHeadlinesNews(query = queryFilter)
                .getOrThrow().articles?.mapNotNull { article ->
                    article?.toDomainModel()
                }
        articles?.let {
            emit(it)
            return@flow
        }
        emit(emptyList())
    }.flowOn(coroutineDispatcher)


    override fun getFavoriteArticles(): Flow<List<Article>> {
        return newsLocalDatasource.getFavoriteNews()
            .map { entities ->
                entities.map { it.toDomainModel() }
            }
            .flowOn(coroutineDispatcher)
    }


    override suspend fun addToFavorite(article: Article) {
        newsLocalDatasource.insert(
            article.apply {
                isFavorite = true
            }.toDatabaseModel()
        )
    }


    override suspend fun removeFromFavorite(article: Article) {
        newsLocalDatasource.insert(
            article.apply {
                isFavorite = false
            }.toDatabaseModel()
        )
    }

    companion object {
        private val CACHE_TIMEOUT: Duration = 30.minutes
    }
}