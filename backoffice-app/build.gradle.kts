plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("dev.vlaship.lombok")
    id("dev.vlaship.unpack")
    id("dev.vlaship.git-properties")
    id("dev.vlaship.graalvm")
    alias(libs.plugins.graalvm.buildtools.native)
}

dependencies {
    implementation(project(":backoffice-api"))

    // avoid warnings
    compileOnly(libs.findbugs)

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // web
    implementation(libs.springdocWeb)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // mapper
    compileOnly(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    // db
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")
    implementation(libs.preLiquibase)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-tracing")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

//graalvmNative {
//    binaries {
//        named("main") {
////            imageName.set("app")
////            useFatJar.set(true)
//            sharedLibrary.set(false)
//            mainClass.set("dev.vlaship.backoffice.App")
//        }
//    }
//    binaries.all {
////        buildArgs.add("--verbose")
//        resources.autodetect()
//    }
////
//    toolchainDetection.set(true)
//}
