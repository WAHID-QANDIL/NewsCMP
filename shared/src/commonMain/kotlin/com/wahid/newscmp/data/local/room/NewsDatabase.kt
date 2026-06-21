package com.wahid.newscmp.data.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.wahid.newscmp.data.local.room.dao.NewsDao
import com.wahid.newscmp.data.local.room.entity.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(TypeAdapters::class)
abstract class NewsDatabase: RoomDatabase(){
    abstract fun getNewsDao(): NewsDao
}
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<NewsDatabase>{
    override fun initialize(): NewsDatabase
}

expect fun getDatabaseBuilder(): RoomDatabase.Builder<NewsDatabase>


fun getRoomDatabase(
    builder: RoomDatabase.Builder<NewsDatabase> = getDatabaseBuilder()
): NewsDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}