
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
//    implementation(libs.android.gradle)
    implementation("com.squareup:javapoet:1.13.0")
}
