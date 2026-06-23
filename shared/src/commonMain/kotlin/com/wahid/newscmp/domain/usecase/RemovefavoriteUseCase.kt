package com.wahid.newscmp.domain.usecase

import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.repository.NewsRepository
import dev.zacsweers.metro.Inject

@Inject
class RemovefavoriteUseCase(
    private val repository: NewsRepository
){
    suspend operator fun invoke(article: Article){
        repository.removeFromFavorite(article = article)
    }
}