package com.wahid.newscmp.domain.usecase

import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.domain.repository.NewsRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

@Inject
class GetHeadlinesUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(query: Map<String, String>): Flow<List<Article>> =
        repository.getHeadLineArticles(queryFilter = query)
}