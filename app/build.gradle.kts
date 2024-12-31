plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.peterchege.composenewsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.peterchege.composenewsapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
    buildTypes {
        getByName("debug") {

        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:1.7.6")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.6")
    implementation("androidx.compose.material:material:1.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.6")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.6")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.6")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.6")
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.14")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    implementation("androidx.paging:paging-compose:3.3.5")
    implementation("androidx.paging:paging-runtime-ktx:3.3.5")

    // datastore (core and preferences)
    implementation("androidx.datastore:datastore:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // dagger hilt
    implementation("com.google.dagger:hilt-android:2.53.1")
    ksp("com.google.dagger:dagger-compiler:2.53.1") // Dagger compiler
    ksp("com.google.dagger:hilt-compiler:2.53.1")   // Hilt compiler
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")

    implementation("com.jakewharton.timber:timber:5.0.1")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")

    // chucker
    debugImplementation("com.github.chuckerteam.chucker:library:4.1.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.1.0")


}