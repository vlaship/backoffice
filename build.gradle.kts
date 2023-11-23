plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("io.spring.javaformat") version "0.0.40"
    id("com.gorylenko.gradle-git-properties") version "2.4.1"
    id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "vlaship"
version = "0.0.3-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val jwtVersion = "0.11.5"
val openApiVersion = "2.2.0"
val mapstructVersion = "1.5.5.Final"
val preLiquibaseVersion = "1.4.0"

dependencies {
    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // mapper
    compileOnly("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // db
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")
    implementation("net.lbruun.springboot:preliquibase-spring-boot-starter:$preLiquibaseVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

gitProperties {
    dateFormat = "yyyy-MM-dd HH:mm:ssZ"
    dateFormatTimeZone = "GMT"
    keys = arrayListOf("git.branch", "git.commit.id", "git.commit.id.abbrev", "git.commit.time", "git.tags")
}

tasks.jar {
    archiveFileName.set("app.jar")
}

tasks {
    bootJar {
        archiveFileName.set("boot.jar")
    }
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("app")
            useFatJar.set(true)
        }
    }
    binaries.all {
        buildArgs.add("--verbose")
        resources.autodetect()
    }

    toolchainDetection.set(true)
}
