buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }}
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")
    }
}
//apply(plugin = "org.jetbrains.kotlin.kapt")
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "8.3.0-rc02" apply false
    id ("com.android.library") version "8.3.0-rc02" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

