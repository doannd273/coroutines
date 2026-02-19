plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.example.coroutines"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.coroutines"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("release.keystore")
            storePassword = "your_password"
            keyAlias="your_alias"
            keyPassword="your_password"
        }
    }

    // kiểu build
    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // signing config => chữ ký build app release
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions += "environment"

    // môi trường build
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix=".dev"
            versionNameSuffix=".dev"

            // domain
            buildConfigField(
                "String",
                "BASE_JSON_PLACE",
                "\"https://jsonplaceholder.typicode.com\""
            )
            buildConfigField(
                "String",
                "BASE_REQRES",
                "\"https://reqres.in\""
            )
            buildConfigField("String", "API_KEY", "\"reqres_c0fce7bb4f75480f8f92833388984f30\"")
        }

        create("production") {
            dimension = "environment"


            // domain
            buildConfigField(
                "String",
                "BASE_JSON_PLACE",
                "\"https://jsonplaceholder.typicode.com\""
            )
            buildConfigField(
                "String",
                "BASE_REQRES",
                "\"https://reqres.in\""
            )
            buildConfigField("String", "API_KEY", "\"reqres_c0fce7bb4f75480f8f92833388984f30\"")
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
        compose = true
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.timber)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

}