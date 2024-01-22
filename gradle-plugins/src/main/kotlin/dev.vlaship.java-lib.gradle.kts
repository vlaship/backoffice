plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

val javaVersion = JavaVersion.VERSION_21

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion.majorVersion)) }
}
