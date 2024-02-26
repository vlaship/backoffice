plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("dev.vlaship.lombok")
    id("dev.vlaship.unpack")
    id("dev.vlaship.git-properties")
}

val jwtVersion = "0.12.3" // "0.11.5" works with graalvm
val openApiVersion = "2.3.0"
val mapstructVersion = "1.5.5.Final"
val preLiquibaseVersion = "1.5.0"
val openTelemetryVersion = "2.0.0"
val micrometerVersion = "1.2.2"
val findbugsVersion = "3.0.2"

dependencies {
    implementation(project(":backoffice-api"))

    // avoid warnings
    compileOnly("com.google.code.findbugs:jsr305:$findbugsVersion")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

    // web
    implementation(libs.springdoc)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // mapper
    compileOnly("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // db
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")
    implementation("net.lbruun.springboot:preliquibase-spring-boot-starter:$preLiquibaseVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:$openTelemetryVersion")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
