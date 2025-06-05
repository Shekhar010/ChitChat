plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.chitchat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.chitchat"
        minSdk = 24
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
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.generativeai)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // dependencies for navigation
    implementation(libs.androidx.navigation.compose)

    // dependency for image loading
    implementation(libs.coil.compose)

    // dependency for hilt
    implementation(libs.hilt.android)

    // Hilt Core
    kapt( libs.hilt.compiler.v250)

    implementation (libs.androidx.hilt.navigation.compose)

    // Lifecycle ViewModel (optional, but recommended)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    // for navigation
    // Navigation for Compose
    implementation(libs.androidx.navigation.compose.v277)

    // Needed for type-safe destinations (safe args in Compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    // dependency for retrofit // gson for json parsing

    // dependency for Cloudinary
    implementation(libs.cloudinary.android.v231)

    // change the ui color





}