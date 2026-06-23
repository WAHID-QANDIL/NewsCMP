package com.wahid.newscmp.presentation.screen.allNews

import com.wahid.newscmp.domain.model.Article


sealed class AllNewsIntent{
    data class SearchQueryChange (val newQuery: String): AllNewsIntent()
    data class AddToFavorite (val article: Article): AllNewsIntent()
    data class RemoveToFavorite (val article: Article): AllNewsIntent()
}
