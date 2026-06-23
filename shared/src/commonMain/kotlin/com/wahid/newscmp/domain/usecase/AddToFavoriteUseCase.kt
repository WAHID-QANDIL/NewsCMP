package com.wahid.newscmp.domain.usecase

import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.repository.NewsRepository
import dev.zacsweers.metro.Inject

@Inject
class AddToFavoriteUseCase(
    private val repository: NewsRepository
){
     suspend operator fun invoke(article: Article){
        repository.addToFavorite(article = article)
     }
}