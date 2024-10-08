package com.blucky8649.brocallie

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform() {

    extensions.configure<KotlinMultiplatformExtension> {
        androidTarget {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
        }

        iosX64()
        iosArm64()
        iosSimulatorArm64()

        task("testClasses")

        sourceSets.apply {
            androidMain.dependencies {
                val koinAndroid = libs.findLibrary("koin-android").get()
                implementation(koinAndroid)
                val koinAndroidxCompose = libs.findLibrary("koin-android-compose").get()
                implementation(koinAndroidxCompose)
            }

            commonMain.dependencies {
                val koinCore = libs.findLibrary("koin-core").get()
                implementation(koinCore)
                val koinCompose = libs.findLibrary("koin-compose").get()
                implementation(koinCompose)
                val datetime = libs.findLibrary("kotlinx-datetime").get()
                implementation(datetime)
                val serializationJson = libs.findLibrary("kotlinx-serialization-json").get()
                implementation(serializationJson)
            }

            commonTest.dependencies {
                val koinTest = libs.findLibrary("koin-test").get()
                implementation(koinTest)
                val test = libs.findLibrary("kotlin-test").get()
                implementation(test)
            }
        }
    }

    extensions.configure<LibraryExtension> {
        namespace = "com.blucky8649.brocallie"
        compileSdk = 34
        defaultConfig {
            minSdk = 24
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}