plugins {
    alias(libs.plugins.android.application)
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")

    //slider view
    implementation ("com.github.smarteist:Android-Image-Slider:1.4.0-appcompat")
    implementation ("com.github.smarteist:Android-Image-Slider:1.4.0")
    implementation ("com.github.bumptech.glide:glide:4.11.0")

    //firebase recycler adapter
    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    implementation("androidx.appcompat:appcompat:1.6.1")  // Ensure this is included
    implementation ("com.google.android.material:material:1.9.0")  // Optional, for Material Design components

   //razorpay
    implementation ("com.razorpay:checkout:1.6.20")
    androidTestImplementation(libs.espresso.contrib)


}