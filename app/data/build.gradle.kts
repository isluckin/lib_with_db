import org.gradle.kotlin.dsl.androidTestImplementation

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id ("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.example.mylibrary"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        buildConfigField(
            "String",
            "GOOGLE_BOOKS_API_KEY",
            "\"AIzaSyCT_rZtzsReYJgxI9Ba_hxMHPaDAz3WYbk\""
        )


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        android.buildFeatures.buildConfig = true
    }
}


ksp{
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":app"))
    implementation ("androidx.room:room-runtime:2.7.1")
    implementation ("androidx.room:room-ktx:2.7.1")
    implementation(project(":app:common"))
    ksp("androidx.room:room-compiler:2.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}