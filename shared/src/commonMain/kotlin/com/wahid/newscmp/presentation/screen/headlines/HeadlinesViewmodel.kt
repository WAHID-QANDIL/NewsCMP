package com.wahid.newscmp.presentation.screen.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahid.newscmp.domain.usecase.GetFavoritesUseCase
import com.wahid.newscmp.domain.usecase.GetHeadlinesUseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@ContributesIntoMap(AppScope::class)
@ViewModelKey(HeadlinesViewmodel::class)
@Inject
class HeadlinesViewmodel(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
) : ViewModel() {

    private val localState = MutableStateFlow(HeadlinesScreenUIState())
    val state = localState.asStateFlow()

    init {
        viewModelScope.launch {
            getHeadlinesUseCase(
                query = buildMap {
                    put("country", "us")
                    put("category", "business")
                }
            ).catch { error ->
                localState.update {
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false
                    )
                }
            }.collect { headlines ->
                localState.update {
                    it.copy(
                        isLoading = false,
                        headlines = headlines
                    )
                }
            }
        }
    }


}