plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id ("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.dagger.hilt.android")

}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("C:\\keystore\\key1.jks")
            storePassword = "20001981e"
            keyAlias = "key1"
            keyPassword = "20001981e"
        }
        create("release") {
            storeFile = file("C:\\keystore\\key1.jks")
            storePassword = "20001981e"
            keyAlias = "key1"
            keyPassword = "20001981e"
        }
    }
    namespace = "com.lightcore.goaltracker_pro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lightcore.goaltracker_pro"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "2"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    flavorDimensions += listOf("release")

}


dependencies {
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.google.firebase:firebase-firestore:24.10.2")
    implementation ("com.google.mlkit:smart-reply:17.0.2")
    implementation ("com.github.prolificinteractive:material-calendarview:1.6.1")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test:runner:1.5.2")
    implementation("com.google.android.libraries.places:places:3.3.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:<latest-version>")
//    Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.44")
}
//kapt {
//    correctErrorTypes = true
//}

