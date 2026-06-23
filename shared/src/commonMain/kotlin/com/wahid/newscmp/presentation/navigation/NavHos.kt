package com.wahid.newscmp.presentation.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                        onBookmarkClick = { TODO() },
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
                                    ""
                                )
                            )
                        },
                        onSearch = { },
                    )
                }
                entry<StackEntries.Headlines> { entry ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                    }
                }
                entry<StackEntries.FavoriteNews> {

                }
            },

            transitionSpec = {
                // Slide in from right when navigating forward
                slideInHorizontally(initialOffsetX = { it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { -it })
            },
            popTransitionSpec = {
                // Slide in from left when navigating back
                // slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it })

                // Slide new content up, keeping the old content in place underneath
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(700)
                ) togetherWith ExitTransition.KeepUntilTransitionsFinished
            },
            predictivePopTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            },
        )

    }


}



