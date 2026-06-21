package com.wahid.newscmp.data.local.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.wahid.newscmp.NewsApp

actual fun getDatabaseBuilder(): RoomDatabase.Builder<NewsDatabase> {
    val appContext = NewsApp.instance
    val dbFile = appContext.getDatabasePath("NewsDatabase.db")
    return Room.databaseBuilder<NewsDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}