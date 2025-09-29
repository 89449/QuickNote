plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.0"
    id("com.google.devtools.ksp")
}

android {
    namespace = "app.qn"
    compileSdk = 36

    defaultConfig {
        applicationId = "app.qn"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        androidResources {
            localeFilters += listOf("en")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.9.1")
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    
    implementation("androidx.room:room-runtime:2.8.1")
	implementation("androidx.room:room-ktx:2.8.1")
	ksp("androidx.room:room-compiler:2.8.1")
}
