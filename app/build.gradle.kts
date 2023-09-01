plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace =  "com.peterchege.composenewsapp"
    compileSdk = 34

    defaultConfig {
        applicationId =  "com.peterchege.composenewsapp"
        minSdk = 21
        targetSdk =  34
        versionCode =  1
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
        getByName("release") {
            isMinifyEnabled =  false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_17
        targetCompatibility =  JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose =  true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =  "1.5.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.activity:activity-compose:1.7.2")
    implementation ("androidx.compose.ui:ui:1.6.0-alpha04")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.0-alpha04")
    implementation ("androidx.compose.material:material:1.6.0-alpha04")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.0-alpha04")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.6.0-alpha04")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.0-alpha04")

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("androidx.compose.material:material-icons-extended:1.6.0-alpha04")
    implementation ("androidx.navigation:navigation-compose:2.7.1")

    // coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("androidx.paging:paging-compose:3.2.0")
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")


    // datastore (core and preferences)
    implementation ("androidx.datastore:datastore:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:dagger-compiler:2.48") // Dagger compiler
    ksp("com.google.dagger:hilt-compiler:2.48")   // Hilt compiler
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // room
    implementation( "androidx.room:room-runtime:2.5.2")
    ksp ("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    implementation ("androidx.room:room-paging:2.5.2")

    implementation("com.jakewharton.timber:timber:5.0.1")
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.12")

    // chucker
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")


}