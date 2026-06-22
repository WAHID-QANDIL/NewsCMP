package com.wahid.newscmp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface StackEntries : NavKey {
    @Serializable
    data object AllNews : StackEntries
    @Serializable
    data object FavoriteNews : StackEntries
    @Serializable
    data object Headlines:StackEntries
}