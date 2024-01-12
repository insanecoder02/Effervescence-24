plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.effervescence.nipher"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.effervescence.nipher"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
}

dependencies {
    implementation ("com.google.android.exoplayer:exoplayer:2.16.1")
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.16.1")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("io.coil-kt:coil:1.4.0")// Use the latest version available
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation ("com.google.firebase:firebase-messaging-directboot:23.2.1")
    implementation ("com.google.firebase:firebase-firestore:24.7.1")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.0")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("com.facebook.shimmer:shimmer:0.5.0@aar")
    implementation ("com.google.firebase:firebase-firestore:24.7.1")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("com.airbnb.android:lottie:6.1.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.core:core-animation:1.0.0-rc01")
    implementation("com.google.firebase:firebase-database:20.3.0")

    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation ("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.sparrow007:carouselrecyclerview:1.2.6")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation("com.github.bumptech.glide:glide:4.13.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0-alpha01")




}