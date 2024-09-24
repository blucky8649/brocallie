package com.blucky8649.brocallie

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureComposeMultiplatform() {
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.apply {
            androidMain.dependencies {
                val navigationCompose = libs.findLibrary("androidx-navigation-compose").get()
                implementation(navigationCompose.get())
                val ktorAndroid = libs.findLibrary("ktor-client-android").get()
                implementation(ktorAndroid.get())
                val bom = libs.findLibrary("androidx-compose-bom").get()
                implementation(project.dependencies.platform(bom))
                val uiToolingPreview = libs.findLibrary("compose-ui-tooling-preview").get()
                implementation(uiToolingPreview)
                val uiTooling = libs.findLibrary("compose-ui-tooling").get()
                implementation(uiTooling)
                val decomposeNavhost = libs.findLibrary("decompose-navhost").get()
                implementation(decomposeNavhost)
            }

            commonMain.dependencies {
                val compose = ComposePlugin.Dependencies(this@configureComposeMultiplatform)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                val viewModel = libs.findLibrary("kmp-viewmodel").get()
                implementation(viewModel)

                val coilCore = libs.findLibrary("coil-core").get()
                implementation(coilCore)
                val coilKtor = libs.findLibrary("coil-ktor").get()
                implementation(coilKtor)
                val coilCompose = libs.findLibrary("coil-compose").get()
                implementation(coilCompose)
                val coilComposeCore = libs.findLibrary("coil-compose-core").get()
                implementation(coilComposeCore)
                val tablerIcon = libs.findLibrary("composeIcons-tablerIcons").get()
                implementation(tablerIcon)
                val decomposeNavhost = libs.findLibrary("decompose-navhost").get()
                implementation(decomposeNavhost)
                val urlEncoder = libs.findLibrary("urlEncoder").get()
                implementation(urlEncoder)
            }

            iosMain.dependencies {
                val ktorIos = libs.findLibrary("ktor-client-ios").get()
                implementation(ktorIos)
            }
        }
    }
    extensions.configure<ComposeExtension>() {
        extensions.configure<ResourcesExtension> {
            publicResClass = true
            generateResClass = always
        }
    }
}