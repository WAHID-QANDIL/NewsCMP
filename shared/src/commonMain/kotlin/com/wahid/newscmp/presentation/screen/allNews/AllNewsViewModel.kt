package com.wahid.newscmp.presentation.screen.allNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahid.newscmp.domain.usecase.AddToFavoriteUseCase
import com.wahid.newscmp.domain.usecase.GetAllNewsUseCase
import com.wahid.newscmp.domain.usecase.RemovefavoriteUseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlin.time.Duration.Companion.milliseconds


@OptIn(FlowPreview::class)
@ContributesIntoMap(AppScope::class)
@ViewModelKey(AllNewsViewModel::class)
@Inject
class AllNewsViewModel(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removefavoriteUseCase: RemovefavoriteUseCase,
) : ViewModel() {

    private val localState = MutableStateFlow<AllNewsUIState>(AllNewsUIState())
    val state = localState.asStateFlow()


    init {
        viewModelScope.launch {
            state.map { it.searchQuery }
                .debounce(2000.milliseconds)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collectLatest {
                    forceFetch(mapOf("q" to it), true)
                }
        }

        fetch(
            buildMap {
                put(
                    key = "q",
                    value = state.value.selectedCategory
                )
            }
        )

    }

    fun onIntent(intent: AllNewsIntent) {
        when (intent) {
            is AllNewsIntent.SearchQueryChange -> {
                localState.update {
                    it.copy(searchQuery = intent.newQuery)
                }
            }

            is AllNewsIntent.AddToFavorite -> {
                viewModelScope.launch {
                    addToFavoriteUseCase(intent.article)
                }
            }

            is AllNewsIntent.RemoveToFavorite -> {
                viewModelScope.launch {
                    removefavoriteUseCase(intent.article)
                }
            }
        }
    }


    private fun fetch(newQuery: Map<String, String>) {
        forceFetch(newQuery = newQuery, false)
    }

    private fun forceFetch(newQuery: Map<String, String>, forceFetch: Boolean) {
        viewModelScope.launch {
            localState.update {
                it.copy(isLoading = true)
            }
            try {
                getAllNewsUseCase(query = newQuery, forceFetch = forceFetch)
                    .catch { error ->
                        localState.update {
                            it.copy(isLoading = false, errorMessage = error.message)
                        }
                    }.collect { news ->
                        localState.update { state ->
                            state.copy(isLoading = false, news = news)
                        }
                    }
            } catch (e: IOException) {
                localState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }

}