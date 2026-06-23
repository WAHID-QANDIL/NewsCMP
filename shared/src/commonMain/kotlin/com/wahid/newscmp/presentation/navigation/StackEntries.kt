package com.wahid.newscmp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import com.wahid.newscmp.domain.model.Article
import kotlinx.serialization.Serializable

@Serializable
sealed interface StackEntries : NavKey {

    @Serializable
    data class Details(val article: Article): StackEntries


    @Serializable
    data object AllNews : StackEntries

    @Serializable
    data object FavoriteNews : StackEntries

    @Serializable
    data object Headlines : StackEntries
}