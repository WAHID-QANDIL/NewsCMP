package com.wahid.newscmp.domain.usecase

import com.wahid.newscmp.domain.repository.NewsRepository
import dev.zacsweers.metro.Inject

@Inject
class GetFavoritesUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke() = repository.getFavoriteArticles()
}