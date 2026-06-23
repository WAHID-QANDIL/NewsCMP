package com.wahid.newscmp.presentation.screen.allNews


sealed class AllNewsIntent{
    data class SearchQueryChange (val newQuery: String): AllNewsIntent()
}
