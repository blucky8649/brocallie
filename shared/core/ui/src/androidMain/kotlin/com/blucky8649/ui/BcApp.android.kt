package com.blucky8649.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.blucky8649.room.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

@Composable
actual fun BcApp(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    BcAndroidTheme {
        KoinApplication(
            application = {
                androidContext(context)
                modules(databaseModule)
            }, content)
    }
}