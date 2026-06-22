package com.wahid.newscmp.presentation.screen.allNews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.presentation.screen.component.AppSearchBar
import com.wahid.newscmp.presentation.screen.component.CategoryFilterRow
import com.wahid.newscmp.presentation.screen.component.ErrorState
import com.wahid.newscmp.utils.Colors.ColorAccent
import com.wahid.newscmp.utils.Colors.ColorBackground
import com.wahid.newscmp.utils.Colors.ColorChip


@Composable
fun AllNewsScreen(
    onNavigateToFavorites: () -> Unit,
    onNavigateToHeadline: () -> Unit,
    state: AllNewsUIState,
    onBookmarkClick: (Article) -> Unit = {},
    searchQuery: String = "",
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        color = ColorBackground,
        modifier = modifier
    ) {
        val categories = remember {
            listOf("Technology", "Business", "Sports", "Health", "Science")
        }
        var selectedCategory by remember { mutableStateOf(categories[0]) }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CategoryFilterRow(categories = categories, selectedCategory, { selectedCategory = it })
            AppSearchBar(
                query = searchQuery,
                onSearch = onSearch,
                onQueryChange = onQueryChange,
                onClearClick = onClearClick,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            when {
                state.isLoading -> {
                    LoadingState()
                }

                state.errorMessage != null -> {
                    ErrorState(
                        message = state.errorMessage,
                        padding = PaddingValues(12.dp)
                    )
                }

                else -> {

                }


            }

        }

    }

}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingState() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularWavyProgressIndicator(
                    color = ColorAccent,
                    trackColor = ColorChip
                )
            }
        }
    }
}