plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.graalvm.gradle.plugin)
    implementation(libs.spring.boot.gradle.plugin)
    implementation(libs.git.properties.gradle.plugin)
}
