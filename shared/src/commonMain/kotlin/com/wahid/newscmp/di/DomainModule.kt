package com.wahid.newscmp.di

import com.wahid.newscmp.domain.repository.NewsRepository
import com.wahid.newscmp.domain.usecase.GetAllNewsUseCase
import com.wahid.newscmp.domain.usecase.GetHeadlinesUseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
interface DomainModule {

    @Provides
    fun provideGetAllNewsUseCase(@Provides repository: NewsRepository): GetAllNewsUseCase =
        GetAllNewsUseCase(
            repository = repository
        )

    @Provides
    fun provideHeadlinesUseCase(@Provides repository: NewsRepository): GetHeadlinesUseCase =
        GetHeadlinesUseCase(
            repository = repository
        )
}