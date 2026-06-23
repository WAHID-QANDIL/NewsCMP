package com.wahid.newscmp.presentation.navigation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.wahid.newscmp.presentation.navigation.DestinationSavedStateSerializer.config
import com.wahid.newscmp.presentation.screen.allNews.AllNewsIntent
import com.wahid.newscmp.presentation.screen.allNews.AllNewsScreen
import com.wahid.newscmp.presentation.screen.allNews.AllNewsViewModel
import com.wahid.newscmp.presentation.screen.allNews.NewsTab
import com.wahid.newscmp.presentation.screen.component.NewsBottomBar
import com.wahid.newscmp.presentation.screen.component.NewsTopBar
import com.wahid.newscmp.presentation.screen.details.DetailsScreen
import com.wahid.newscmp.presentation.screen.favorites.FavoriteScreenIntent
import com.wahid.newscmp.presentation.screen.favorites.FavoriteViewmodel
import com.wahid.newscmp.presentation.screen.favorites.FavoritesScreen
import com.wahid.newscmp.presentation.screen.headlines.HeadlinesScreen
import com.wahid.newscmp.presentation.screen.headlines.HeadlinesViewmodel
import com.wahid.newscmp.utils.Colors.ColorBackground
import dev.zacsweers.metrox.viewmodel.metroViewModel


@Composable
fun AppNavigationHost(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(config, StackEntries.AllNews)
    var selectedTab by remember { mutableStateOf(NewsTab.ALL_NEWS) }

    fun onNavigation(selectedTabf: NewsTab, newsTab: NewsTab) {
        when {
            newsTab == NewsTab.ALL_NEWS && newsTab != selectedTabf -> {
                backStack.add(StackEntries.AllNews)
            }

            newsTab == NewsTab.HEADLINES && newsTab != selectedTabf -> {
                backStack.add(StackEntries.Headlines)
            }

            newsTab == NewsTab.FAVORITES && newsTab != selectedTabf -> {
                backStack.add(StackEntries.FavoriteNews)
            }
        }
        selectedTab = newsTab


    }
    Scaffold(
        containerColor = ColorBackground,
        bottomBar = {
            NewsBottomBar(selectedTab = selectedTab, onTabSelected = {
                onNavigation(selectedTabf = selectedTab, newsTab = it)
            }
            )
        },
        topBar = {
            NewsTopBar()
        }
    ) {
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<StackEntries.AllNews> {
                    val viewModel = metroViewModel<AllNewsViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    AllNewsScreen(
                        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(
                            top = 70.dp
                        ),
                        state = state,
                        onBookmarkClick = { article ->
                            val isFavorite = article.isFavorite
                            when {
                                isFavorite -> {
                                    viewModel.onIntent(
                                        intent = AllNewsIntent.RemoveToFavorite(
                                            article
                                        )
                                    )
                                }

                                !isFavorite -> {
                                    viewModel.onIntent(
                                        intent = AllNewsIntent.AddToFavorite(
                                            article
                                        )
                                    )
                                }
                            }
                        },
                        searchQuery = state.searchQuery,
                        onQueryChange = {
                            viewModel.onIntent(
                                intent = AllNewsIntent.SearchQueryChange(
                                    it
                                )
                            )
                        },
                        onClearClick = {
                            viewModel.onIntent(
                                intent = AllNewsIntent.SearchQueryChange(
                                    "General"
                                )
                            )
                        },
                        onSearch = {

                        },
                        onArticleClick = { article ->
                            backStack.add(StackEntries.Details(article))
                        }
                    )
                }
                entry<StackEntries.Headlines> {
                    val viewmodel = metroViewModel<HeadlinesViewmodel>()
                    val state by viewmodel.state.collectAsStateWithLifecycle()

                    HeadlinesScreen(
                        articles = state.headlines,
                        onArticleClick = {
                                backStack.add(StackEntries.Details(article = it))
                        },
                        modifier = Modifier.padding(vertical = 70.dp)
                    )
                }
                entry<StackEntries.FavoriteNews> {
                    val viewModel = metroViewModel<FavoriteViewmodel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    FavoritesScreen(
                        articles = state.favoriteArticles,
                        onRemoveFavorite = {
                            viewModel.onIntent(
                                intent = FavoriteScreenIntent.RemoveFavorite(
                                    it.copy(isFavorite = false)
                                )
                            )
                        },
                        onArticleClick = {
                            backStack.add(StackEntries.Details(it))
                        },
                        modifier = Modifier.padding(vertical = 70.dp)
                    )
                }
                entry<StackEntries.Details> {
                    DetailsScreen(
                        article = it.article,
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        modifier = Modifier.padding(vertical = 80.dp)
                    )


                }
            },
            transitionSpec = {
                slideInVertically(
                    initialOffsetY = { it }
                ) togetherWith slideOutVertically(
                    targetOffsetY = { -it }
                )
            },
            popTransitionSpec = {
                slideInVertically(
                    initialOffsetY = { -it }
                ) togetherWith slideOutVertically(
                    targetOffsetY = { it }
                )
            }
        )

    }


}



