import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}
val useTiramisu = project.properties["useTiramisu"].toString().toBoolean()
val sdkVersion = libs.versions.particle.network.version.get()
val pnCompileSdk = if (useTiramisu) {
    libs.versions.compileSdkTiramisu.get().toInt()
} else {
    libs.versions.compileSdk.get().toInt()
}
val pnTargetSdk = if (useTiramisu) {
    libs.versions.targetSdkTiramisu.get().toInt()
} else {
    libs.versions.targetSdk.get().toInt()
}

android {

    compileSdk = pnCompileSdk
    defaultConfig {
        applicationId = "network.particle.demos"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = pnTargetSdk
        versionCode = 5
        versionName = "$sdkVersion"
        vectorDrawables {
            useSupportLibrary = true
        }
        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
        }

        manifestPlaceholders["PN_PROJECT_ID"] = "864a5dd6-9fa2-450e-88f6-0920348d069c"
        manifestPlaceholders["PN_PROJECT_CLIENT_KEY"] = "cVq5PW9A8D5eQmps6ugwty2nfKpG0W825ijoqXk8"
        manifestPlaceholders["PN_APP_ID"] = "3f574cac-267e-4ffa-aa2e-f284ab81e95e"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    dataBinding {
        isEnabled = true
    }
}




dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    //auth-service and auth-service-tiramisu are the same library,
    //tiramisu requires android targetSdkVersion 33,it supports auth web half screen
    if (useTiramisu) {
        implementation("network.particle:auth-service-tiramisu:$sdkVersion")
    } else {
        implementation("network.particle:auth-service:$sdkVersion")
    }
    implementation("network.particle:api-service:$sdkVersion")
    implementation("network.particle:wallet-service:$sdkVersion")

    //if you want to use biconomy service,please add this dependency
    implementation("network.particle:biconomy-service:$sdkVersion")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.bundles.retrofit)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.utilcodex)
    implementation(libs.refresh.layout)

    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.coil.gif)
    implementation(libs.immersionbar)
    implementation(libs.bannerviewpager)

}

tasks.withType<Test> {
    useJUnitPlatform()
}