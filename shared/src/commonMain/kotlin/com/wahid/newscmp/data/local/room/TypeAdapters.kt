package com.wahid.newscmp.data.local.room

import androidx.room.TypeConverter
import com.wahid.newscmp.data.local.room.entity.ArticleEntity
import kotlinx.serialization.json.Json
import kotlin.time.Instant

class TypeAdapters {
    @TypeConverter
    fun<T> fromListToString(items: List<ArticleEntity> ): String{
        return Json.encodeToString(items)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<ArticleEntity> {
        return Json.decodeFromString(string)
    }



    @TypeConverter
    fun fromStringToQuery(value: String?) =
        value?.let {
            Json.decodeFromString<Map<String, String>>(it)
        }

    @TypeConverter
    fun fromQueryToString(value: Map<String, String>?) = value?.toString()



    @TypeConverter
    fun stringToInstant(value: String?) = value?.let { Instant.parse(it) }

    @TypeConverter
    fun instantToString(value: Instant?) = value?.toString()

}

