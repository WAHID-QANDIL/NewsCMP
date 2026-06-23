package com.wahid.newscmp.presentation.screen.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.utils.Colors.ColorBackground
import com.wahid.newscmp.utils.Colors.ColorTextMuted
import com.wahid.newscmp.utils.Colors.ColorTextPrimary

@Composable
fun FavoritesScreen(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onRemoveFavorite: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBackground)

    ) {

        Text(
            text = "Favorites",
            style = MaterialTheme.typography.headlineMedium,
            color = ColorTextPrimary,
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 24.dp
            )
        )

        Text(
            text = "${articles.size} saved articles",
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextMuted,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (articles.isEmpty()) {

            EmptyFavorites()

        } else {

            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = articles,
                    key = { it.url }
                ) { article ->

                    FavoriteArticleItem(
                        article = article,
                        onClick = {
                            onArticleClick(article)
                        },
                        onRemoveFavorite = {
                            onRemoveFavorite(article)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    FavoritesScreen(
        articles = listOf(
            Article(
                id = "1",
                author = "TODO()",
                isFavorite = true,
                content = "TODO()",
                description = "TODO()",
                publishedAt = "TODO()",
                title = "TODO()",
                source = "TODO()",
                url = "TODO()1",
                urlToImage = "TODO()"
            ),
            Article(
                id = "2",
                author = "TODO()",
                isFavorite = true,
                content = "TODO()",
                description = "TODO()",
                publishedAt = "TODO()",
                title = "TODO()",
                source = "TODO()",
                url = "TODO()2",
                urlToImage = "TODO()"
            ),
            Article(
                id = "3",
                author = "TODO()",
                isFavorite = true,
                content = "TODO()",
                description = "TODO()",
                publishedAt = "TODO()",
                title = "TODO()",
                source = "TODO()",
                url = "TODO()3",
                urlToImage = "TODO()"
            ),
        ),
        onArticleClick = {},
        onRemoveFavorite = {}
        )
}