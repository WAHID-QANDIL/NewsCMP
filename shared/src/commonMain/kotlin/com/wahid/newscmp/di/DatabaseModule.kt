package com.wahid.newscmp.di

import com.wahid.newscmp.data.local.room.AppDatabaseConstructor
import com.wahid.newscmp.data.local.room.NewsDatabase
import com.wahid.newscmp.data.local.room.dao.NewsDao
import com.wahid.newscmp.data.local.room.getRoomDatabase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
interface DatabaseModule {

    @Provides
    @SingleIn(AppScope::class)
    fun provideRoomDatabase(): NewsDatabase{
        return getRoomDatabase()
    }

    @Provides fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao{
        return newsDatabase.getNewsDao()
    }

}