plugins {
    alias(libs.plugins.brocallie.kotlin.multiplatform.shared)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
        dependencies {
            api(libs.androidx.room.runtime)
            api(libs.androidx.sqlite.bundled)
        }
    }

}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", libs.androidx.room.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata" ) {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}