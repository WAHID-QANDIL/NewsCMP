package com.wahid.newscmp.presentation.screen.favorites

import com.wahid.newscmp.domain.model.Article

data class FavoriteUIState(
    val favoriteArticles: List<Article> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)
