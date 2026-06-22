package com.wahid.newscmp.presentation.screen.allNews

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

sealed class AllNewsIntent{
    data class SearchQueryChange (val newQuery: String): AllNewsIntent()
    data object Search : AllNewsIntent()
}
