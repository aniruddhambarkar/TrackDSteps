plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")

    kotlin("kapt")
}

android {
    namespace = "com.aniruddhambarkar.trackdsteps"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aniruddhambarkar.trackdsteps"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.connect.client.v110alpha11)
    implementation(libs.live.event)
    implementation(libs.splash.screen)
    implementation(libs.play.services.auth)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5") // Check for the latest version
    implementation(libs.androidx.constraintlayout)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

//    implementation(file("libs/samsung-health-data-api-1.0.0.aar"))
//    implementation(libs.dagger)
//    kapt(libs.dagger)
    implementation("com.google.dagger:dagger:2.51.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    kapt("com.google.dagger:dagger-compiler:2.51.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}