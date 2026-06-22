package com.wahid.newscmp.domain.usecase

import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.repository.NewsRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

@Inject
class GetAllNewsUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(
        query: Map<String, String>,
        forceFetch: Boolean
    ): Flow<List<Article>> = repository.getAllNews(queryFilter = query, forceFetch)
}