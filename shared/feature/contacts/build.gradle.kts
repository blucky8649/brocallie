plugins {
    alias(libs.plugins.brocallie.kotlin.multiplatform.shared)
    alias(libs.plugins.brocallie.compose.multiplatform.shared)
    alias(libs.plugins.brocallie.core.designsystem)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.shared.core.room)
    }
}