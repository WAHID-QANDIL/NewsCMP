package com.wahid.newscmp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface BaseDao<T> {
    @Update
    suspend fun update(item:T)

    @Update
    suspend fun update(items: List<T>)

    @Delete
    suspend fun delete(item: T)

    @Upsert
    suspend fun upsert(item: T)

    @Upsert
    suspend fun upsert(items: List<T>)

}