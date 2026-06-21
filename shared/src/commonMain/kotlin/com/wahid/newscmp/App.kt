package com.wahid.newscmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import co.touchlab.kermit.Logger
import com.wahid.newscmp.di.AppGraph
import dev.zacsweers.metro.createGraph
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach


val appGraph = createGraph<AppGraph>()
val newsRepository = appGraph.newsRepository
val logger = Logger.withTag("NewsScreen")

@Composable
@Preview
fun App() {
    MaterialTheme {

        LaunchedEffect(Unit){
            newsRepository.getAllNews(mapOf("q" to "bitcoin"),false)
                .onEach { articles ->
                    logger.d { "Articles count: ${articles.size}" }
                    logger.d { "First: ${articles.firstOrNull()}" }
                }
                .catch { e -> logger.e(e) { "Flow error" } }
                .collect { articles ->
                    // update UI state
                }

        }
    }
}