package com.wahid.newscmp.presentation.screen.allNews

import com.wahid.newscmp.domain.model.Article

data class AllNewsUIState(
    val isLoading: Boolean = false,
    val news: List<Article> = emptyList(),
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val selectedCategory: String = "",
)
