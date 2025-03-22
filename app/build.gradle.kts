plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
 //   alias(libs.plugins.googleServices)
}

android {
    namespace = "com.syeddev.medialibraryapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.syeddev.medialibraryapp"
        minSdk = 23
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
    //AndroidX
    implementation(libs.android.activity)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.viewModel)
    implementation(libs.app.compat)
    implementation(libs.core.ktx)
    implementation(libs.core.animation)
    implementation(libs.splash.screen)
    implementation(libs.multiDex)
    implementation(libs.securityCrypto)
    implementation(libs.annotation)
    implementation(libs.kotlin)

    //compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.compiler)
    implementation(libs.compose.material3)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.navigation)
    implementation(libs.compose.adaptiveNavigation)
    implementation(libs.compose.paging)
    implementation(libs.compose.materialicon)
    implementation(libs.compose.runtime)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.lifecycle.runtime.compose)
    androidTestImplementation(platform(libs.composeBom))
    debugImplementation(libs.composeUiTooling)
    implementation(libs.androidx.ui.text.google.fonts)

    //Coroutines
    implementation(libs.coroutines)
    implementation(libs.coroutines.core)

    //webkit
    implementation(libs.webkit)

    //Dagger-Hilt
    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.navigation)
    debugImplementation(libs.ui.test.manifest)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.dagger.hilt.androidx)

    //Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.ktor)


    //Lottie-Animation
    implementation(libs.lottie.compose)

    //Work-manager
    implementation(libs.work.manager)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.store)
    //implementation(libs.firebase.crashlytics)

    //DataStore
    implementation(libs.data.store.core)

    //Paging3
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)

    //google maps for jetpack compose
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)
    implementation(libs.maps.compose.widjets)
    implementation(libs.play.services.location)
    //implementation(libs.play.services)

    //Room-db
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.work.manager)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.composeBom))

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.mockito)
    androidTestImplementation(libs.mockitoCore)
    androidTestImplementation(libs.composeUiTesting)
}