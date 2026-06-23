package com.wahid.newscmp.presentation.screen.headlines

import com.wahid.newscmp.domain.model.Article

data class HeadlinesScreenUIState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val headlines: List<Article> = emptyList()
)