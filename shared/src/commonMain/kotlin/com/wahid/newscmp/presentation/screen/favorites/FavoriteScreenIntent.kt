package com.wahid.newscmp.presentation.screen.favorites

import com.wahid.newscmp.domain.model.Article

sealed class FavoriteScreenIntent{
    data class RemoveFavorite(val article: Article): FavoriteScreenIntent()
}