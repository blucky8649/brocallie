plugins {
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.google.services)
    alias(libs.plugins.brocallie.android.application.compose).apply(true)
}

android {
    namespace = "com.blucky8649.brocallie"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.blucky8649.brocallie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(projects.shared.core.ui)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.storage.ktx)
    implementation(libs.decompose.core)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.androidx.navigation.testing)
}