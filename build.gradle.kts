buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.1") // ✅ Compatible with Gradle 8.11.1
        classpath("com.google.gms:google-services:4.3.15") // ✅ For Firebase/Google Sign-In
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
