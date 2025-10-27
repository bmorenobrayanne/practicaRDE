plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // 🔹 Si usas anotaciones (Room, Hilt, etc.)
    id("com.google.devtools.ksp")
    // 🔹 Firebase (opcional si lo manejas desde el BOM)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mvp_aplicacionrde"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mvp_aplicacionrde"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // 🔸 Dependencias básicas Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // 🔹 --- ROOM (Base de datos local) ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    ksp("androidx.room:room-compiler:2.8.3")

    // 🔹 --- FIREBASE (Backend remoto) ---
    implementation(platform("com.google.firebase:firebase-bom:34.4.0")) // Or a more recent version

    // Add the dependency for the Firebase Authentication library
    // WITHOUT the -ktx suffix
    implementation("com.google.firebase:firebase-auth")

    // You can also add other Firebase products without specifying their versions
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    // 🔹 --- RETROFIT + JSON (para APIs externas) ---
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Si prefieres Moshi:
    // implementation("com.squareup.retrofit2:converter-moshi:2.11.0")

    // 🔹 --- COROUTINES + LIFECYCLE ---
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // 🔹 --- IMÁGENES (Coil o Glide) ---
    implementation(libs.coil)
    // Alternativa:
    // implementation("com.github.bumptech.glide:glide:4.16.0")
    // kapt("com.github.bumptech.glide:compiler:4.16.0")

    // 🔹 --- TESTING ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // classpath(libs.google.services)

    implementation("io.coil-kt:coil:2.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
