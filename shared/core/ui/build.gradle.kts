plugins {
    alias(libs.plugins.brocallie.kotlin.multiplatform.shared)
    alias(libs.plugins.brocallie.compose.multiplatform.shared)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.shared.core.room)
        api(projects.shared.feature.contacts)
        api(projects.shared.feature.conversation)
        api(projects.shared.feature.createcallie)
    }
}
