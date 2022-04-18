plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.zpw.stockanalyze"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(files("libs/chart-release.aar"))
    implementation(libs.corektx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso)

    // 网络请求
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // 协程
    implementation(libs.kotlinx.coroutines.android)

    // lifecycle
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)

    // hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    // 日志
    implementation(libs.timber)

    // loading
    implementation(libs.loadingDialog)

    //RXText
    implementation(libs.rxbinding)
    //BRVAH
    implementation(libs.baseRecyclerViewAdapterHelper)
}