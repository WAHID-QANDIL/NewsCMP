package com.wahid.newscmp.presentation.screen.allNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahid.newscmp.domain.usecase.GetAllNewsUseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlin.time.Duration.Companion.milliseconds


@OptIn(FlowPreview::class)
@ContributesIntoMap(AppScope::class)
@ViewModelKey(AllNewsViewModel::class)
@Inject
class AllNewsViewModel(
    private val getAllNewsUseCase: GetAllNewsUseCase
) : ViewModel() {

    private val localState = MutableStateFlow<AllNewsUIState>(AllNewsUIState())
    val state = localState.asStateFlow()


    init {
        viewModelScope.launch {
            state.stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000.milliseconds),
                initialValue = AllNewsUIState()
            ).debounce(3000.milliseconds)
                .distinctUntilChanged()
                .collect {
                    forceFetch(mapOf("q" to it.searchQuery), true)
                }
        }

//        fetch(emptyMap())
        forceFetch(
            buildMap {
                put(
                    key = "q",
                    value = "business"
                )
            },
            forceFetch = true
        )
    }

    fun onIntent(intent: AllNewsIntent) {
        when (intent) {
            is AllNewsIntent.SearchQueryChange -> {
                localState.update {
                    it.copy(searchQuery = intent.newQuery)
                }
            }

            AllNewsIntent.Search -> {
                onSearch()
            }
        }
    }

    private fun onSearch() {
        viewModelScope.launch {
            state.debounce(3000.milliseconds)
                .distinctUntilChanged()
                .collect {
                    forceFetch(mapOf("q" to it.searchQuery), true)
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
                getAllNewsUseCase(query = newQuery, forceFetch = false)
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