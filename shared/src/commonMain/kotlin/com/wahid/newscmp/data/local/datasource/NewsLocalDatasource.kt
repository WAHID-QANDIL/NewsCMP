package com.wahid.newscmp.data.local.datasource

import com.wahid.newscmp.data.local.room.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface NewsLocalDatasource {

    fun getAllNews(): Flow<List<ArticleEntity>>
    suspend fun insert(items:List<ArticleEntity>)
    fun getFavoriteNews(): Flow<List<ArticleEntity>>
    fun isFavoriteNews(id: Long): Flow<Boolean>

}