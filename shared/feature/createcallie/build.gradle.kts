import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.brocallie.kotlin.multiplatform.shared)
    alias(libs.plugins.brocallie.compose.multiplatform.shared)
    alias(libs.plugins.brocallie.core.designsystem)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildKonfig)
}

buildkonfig {
    packageName = "com.blucky8649.brocallie.shared.feature.createcallie"

    defaultConfigs {
        val prompt = project.findProperty("PROMPT_ANALYZE") as String
        buildConfigField(FieldSpec.Type.STRING, "PROMPT_ANALYZE", prompt)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.shared.core.firebase)
            implementation(projects.shared.core.generativeAi)
            implementation(projects.shared.core.room)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
