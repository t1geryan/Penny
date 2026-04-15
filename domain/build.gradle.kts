plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":core:coroutines"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.javax.inject)

    implementation(libs.androidx.compose.runtime.annotation)
}

tasks.compileKotlin {
    dependsOn(tasks.named("detekt"))
}
