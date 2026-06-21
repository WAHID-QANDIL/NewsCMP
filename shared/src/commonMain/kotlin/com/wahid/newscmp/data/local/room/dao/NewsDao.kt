package com.wahid.newscmp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.wahid.newscmp.data.local.room.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao : BaseDao<ArticleEntity> {

    @Query(
        """
            SELECT * 
            FROM Article 
            WHERE :query
        """
    )
    fun getAllNews(query: Map<String, String>): Flow<List<ArticleEntity>>


    @Query(
        """
            SELECT * 
            FROM Article
            ORDER BY lastUpdate
            DESC
        """
    )
    fun getAllNews(): Flow<List<ArticleEntity>>

    @Query(
        """
            SELECT * 
            FROM Article 
            WHERE isFavorite=TRUE
        """
    )
    fun getAllFavoriteNews(): Flow<List<ArticleEntity>>

    @Query(
        """
             SELECT EXISTS(SELECT 1
                      FROM article
                      WHERE id = :id)
        """
    )
    fun isFavorite(id: Long): Flow<Boolean>

}