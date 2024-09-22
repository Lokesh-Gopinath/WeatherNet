
// Top-level build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

buildscript {
    dependencies {
        // Firebase services, Google Services plugin
        classpath("com.google.gms:google-services:4.4.2") // Ensure the correct version of Google Services plugin
    }
}

