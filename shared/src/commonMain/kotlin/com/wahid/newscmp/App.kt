package com.wahid.newscmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.touchlab.kermit.Logger
import com.wahid.newscmp.di.AppGraph
import com.wahid.newscmp.presentation.navigation.AppNavigationHost
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory


val appGraph = createGraph<AppGraph>()
val newsRepository = appGraph.newsRepository
val logger = Logger.withTag("NewsScreen")

@Composable
@Preview
fun App() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalMetroViewModelFactory provides appGraph.metroViewModelFactory

        ) {
            AppNavigationHost(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}