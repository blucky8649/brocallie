package com.blucky8649.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.blucky8649.decompose_navhost.navigation.rememberNavController

@Composable
expect fun BcApp(content: @Composable () -> Unit)

@Composable
fun App(
    componentContext: ComponentContext
        = remember { DefaultComponentContext(LifecycleRegistry()) }
) {
    BcApp {
        val navController = rememberNavController(componentContext)
        BcNavHost(navController)
    }
}