package com.wahid.newscmp.data.local.datasource

import com.wahid.newscmp.data.local.room.dao.NewsDao
import com.wahid.newscmp.data.local.room.entity.ArticleEntity
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

@Inject
@ContributesBinding(AppScope::class)
class NewsLocalDatasourceImpl(
    private val dao: NewsDao
) : NewsLocalDatasource {
    override fun getAllNews(): Flow<List<ArticleEntity>> {
        return dao.getAllNews()
    }

    override suspend fun insert(items: List<ArticleEntity>) {
        dao.upsert(items = items)
    }

    override fun getFavoriteNews(): Flow<List<ArticleEntity>> {
        return dao.getAllFavoriteNews()
    }

    override fun isFavoriteNews(id: Long): Flow<Boolean> {
        return dao.isFavorite(id = id)
    }


}