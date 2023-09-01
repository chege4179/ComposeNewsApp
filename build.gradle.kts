plugins {
    id("com.diffplug.spotless") version "5.3.0"
    id ("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
}
subprojects{
    apply(plugin = "com.google.devtools.ksp")
    apply(plugin = "dagger.hilt.android.plugin")
}

buildscript {

    dependencies {
        classpath ("com.android.tools.build:gradle:7.4.2")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath ("com.google.gms:google-services:4.3.14")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.9.10")
    }
}

apply(plugin = "com.diffplug.spotless")
spotless {
    kotlin {
        target("**/*.kt")
        licenseHeaderFile(
            rootProject.file("${project.rootDir}/spotless/LICENSE.txt"),
            "^(package|object|import|interface)"
        )
    }
}

