package com.blucky8649.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.blucky8649.room.databaseModule
import org.koin.compose.KoinApplication

@OptIn(ExperimentalCoilApi::class)
@Composable
actual fun BcApp(
    content: @Composable () -> Unit
) {
    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()
        }
        KoinApplication(
            application = {
                modules(databaseModule)
            }, content)
    }
}

fun BcViewController() = ComposeUIViewController {
    App()
}