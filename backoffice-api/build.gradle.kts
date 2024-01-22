plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("dev.vlaship.lombok")
}

dependencies{
    implementation(libs.springdoc)
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.springframework.data:spring-data-commons")
}
