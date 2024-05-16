plugins {
    java
    idea
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("dev.vlaship.lombok")
    alias(libs.plugins.openapi.generator)
}

dependencies {
    implementation(libs.springdocWeb)
    implementation(libs.springdocData)
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation(libs.jackson.databind.nullable)
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation (libs.swagger.annotations)
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-context")
    implementation("org.hibernate.validator:hibernate-validator")
}

openApiGenerate {
    generatorName = "spring"
    library = "spring-cloud"
    inputSpec = "$rootDir/backoffice-api/api.yaml"
    apiPackage = "dev.vlaship.backoffice.api"
    modelPackage = "dev.vlaship.backoffice.dto"
    generateModelDocumentation = false
    generateApiDocumentation = false
    generateModelTests = false
    generateApiTests = false
    configOptions = mapOf(
        "useTags" to "true",
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "serializableModel" to "true",
        "useBeanValidation" to "true",
        "performBeanValidation" to "true",
        "generateSupportFiles" to "false",
        "gradleBuildFile" to "false",
        "documentationProvider" to "none",
    )
    schemaMappings = mapOf(
        "ProblemDetail" to "org.springframework.http.ProblemDetail",
        "Pageable" to "org.springframework.data.domain.Pageable",
    )
}

sourceSets.getByName("main").java.srcDirs("${project.layout.buildDirectory.get().asFile}/generate-resources/main/src/main/java")

tasks.named<JavaCompile>("compileJava") {
    dependsOn("openApiGenerate")
}
