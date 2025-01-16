plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tc.reading"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tc.reading"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    dataBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.3")
    implementation("com.google.android.material:material:1.12.0")

    implementation("com.github.CarGuo.GSYVideoPlayer:gsyvideoplayer:v10.0.0")
    // implementation("com.github.CarGuo.GSYVideoPlayer:gsyvideoplayer-java:v10.0.0")
    implementation("com.github.CarGuo.GSYVideoPlayer:gsyvideoplayer-exo2:v10.0.0")

    implementation("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")
    implementation(project(":basic"))

    // pull refresh
    implementation("com.github.SimformSolutionsPvtLtd:SSPullToRefresh:1.5.2")

    // blur
    implementation("com.github.mmin18:realtimeblurview:1.2.1")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")

    // banner
    implementation("com.github.zhpanvip:bannerviewpager:3.5.12")
    implementation("androidx.activity:activity:1.9.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}