plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.bitcoding.bitbrains"
    // targetSdk 35 per the integration guide, but this machine only has the
    // android-34 platform installed, so compile/target stay at 34.
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bitcoding.bitbrains"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // The custom URI scheme the backend redirects to after Microsoft login.
        // Must match FRONTEND_REDIRECT_URL on the backend and the deep-link
        // <data> filter in AndroidManifest.xml (see MOBILE_APP_INTEGRATION.md §6.3).
        buildConfigField("String", "OAUTH_REDIRECT_SCHEME", "\"bitbrains\"")
        buildConfigField("String", "OAUTH_REDIRECT_HOST", "\"auth\"")

        // Production backend URL (used by the release build). Point this at the
        // backend team's HTTPS host before shipping. Never hardcode in call sites.
        buildConfigField("String", "API_BASE_URL", "\"https://your-backend.example.com/\"")
    }

    buildTypes {
        debug {
            // Shared ngrok tunnel to the team's backend — a public HTTPS URL, so
            // it works over any network with no `adb reverse` needed.
            // (For a purely local backend instead, use "\"http://127.0.0.1:8000/\""
            // with `adb reverse tcp:8000 tcp:8000`, or "\"http://10.0.2.2:8000/\""
            // on the standard emulator.)
            buildConfigField("String", "API_BASE_URL", "\"https://corey-unalacritous-elvira.ngrok-free.dev/\"")
        }
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-ktx:1.9.1")

    // Chrome Custom Tabs — Microsoft login lives in the browser, never in a WebView.
    implementation("androidx.browser:browser:1.8.0")

    // EncryptedSharedPreferences — session token / email / fcm token at rest.
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // WorkManager — retry queue for device registration and READ/IGNORED actions.
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // Room — local history of received alerts + their sync state.
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Firebase Cloud Messaging
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}
