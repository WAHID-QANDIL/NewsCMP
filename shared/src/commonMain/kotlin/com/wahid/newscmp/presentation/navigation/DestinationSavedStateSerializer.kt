package com.wahid.newscmp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

object DestinationSavedStateSerializer {

    val serializer = SerializersModule {
        polymorphic(NavKey::class){
            subclass(StackEntries.AllNews::class, StackEntries.AllNews.serializer())
            subclass(StackEntries.Headlines::class, StackEntries.Headlines.serializer())
            subclass(StackEntries.FavoriteNews::class, StackEntries.FavoriteNews.serializer())
            subclass(StackEntries.Details::class, StackEntries.Details.serializer())
        }
    }

    val config = SavedStateConfiguration {
        serializersModule = serializer
    }

}