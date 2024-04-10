plugins {

    alias(libs.plugins.androidApplication)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // Your existing dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.material)
    // Other firebase and app dependencies...

    // Unit Testing
    testImplementation ("junit:junit:4.13.2") // JUnit for unit testing
    testImplementation ("org.mockito:mockito-core:3.3.3") // Mockito for mocking in tests

    // Android Instrumentation Tests
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    // Add this if not already included via the libs variable
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.4.1")

    // Existing AndroidX and desugaring dependencies
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation("androidx.multidex:multidex:2.0.1")
}
