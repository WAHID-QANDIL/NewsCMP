package com.wahid.newscmp.presentation.screen.headlines
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.utils.Colors.ColorBackground
import com.wahid.newscmp.utils.Colors.ColorTextPrimary

@Composable
fun HeadlinesScreen(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {

        if (articles.isEmpty()) {
            EmptyHeadlines()
            return@Box
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            item {
                Text(
                    text = "Top Headlines",
                    color = ColorTextPrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    )
                )
            }

            item {
                HeroHeadlineCard(
                    article = articles.first(),
                    onClick = { onArticleClick(articles.first()) }
                )
            }

            item {
                Text(
                    text = "Latest News",
                    color = ColorTextPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    )
                )
            }

            items(
                articles.drop(1),
                key = { it.url }
            ) { article ->

                HeadlineItem(
                    article = article,
                    onClick = { onArticleClick(article) },
                )
            }
        }
    }
}