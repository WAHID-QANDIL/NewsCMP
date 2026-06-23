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
    ): Flow<List<Article>> {
        val queryFilter = mutableMapOf<String, String>()
        queryFilter.apply { this += query }

        if (queryFilter.contains("q").not()) {
            queryFilter["q"] = "General"
        }
        return repository.getAllArticles(queryFilter = query, forceFetch)
    }
}