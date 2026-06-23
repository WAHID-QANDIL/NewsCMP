package com.wahid.newscmp.presentation.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.usecase.GetFavoritesUseCase
import com.wahid.newscmp.domain.usecase.RemovefavoriteUseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@ContributesIntoMap(AppScope::class)
@ViewModelKey(FavoriteViewmodel::class)
@Inject
class FavoriteViewmodel(
    private val removeFavoriteUseCase: RemovefavoriteUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
) : ViewModel() {

    private val localState = MutableStateFlow(FavoriteUIState())
    val state = getFavoritesUseCase()
        .map {
            FavoriteUIState(favoriteArticles = it)
        }
        .catch { error ->
            localState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = error.message
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = FavoriteUIState()
        )

    fun onIntent(intent: FavoriteScreenIntent) {
        when (intent) {
            is FavoriteScreenIntent.RemoveFavorite -> {
                viewModelScope.launch {
                    removeFavoriteUseCase(intent.article)
                }
            }
        }
    }


}