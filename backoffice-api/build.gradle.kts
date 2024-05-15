plugins {
    java
    idea
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("dev.vlaship.lombok")
//    id("org.openapi.generator") version "7.5.0"
}

dependencies {
    implementation(libs.springdocWeb)
    implementation(libs.springdocData)
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.21")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-context")
}

//val buildDir = layout.buildDirectory.dir("generated").get()
//
//idea.module {
//    generatedSourceDirs.add(layout.buildDirectory.dir("generated/src/main/java").get().asFile)
//}
//
//openApiGenerate {
//    generatorName = "spring"
//    library = "spring-cloud"
//    inputSpec = "$rootDir/backoffice-api/api.yaml"
//    outputDir = "$buildDir"
//    apiPackage = "dev.vlaship.backoffice.api"
//    modelPackage = "dev.vlaship.backoffice.dto"
//    generateModelDocumentation = false
//    generateApiDocumentation = false
//    generateModelTests = false
//    generateApiTests = false
//    configOptions = mapOf(
//        "useTags" to "true",
//        "interfaceOnly" to "true",
//        "useSpringBoot3" to "true",
//        "serializableModel" to "true",
//        "useBeanValidation" to "true",
//        "performBeanValidation" to "true",
//        "generateSupportFiles" to "false",
//    )
//    schemaMappings = mapOf(
//        "ProblemDetail" to "org.springframework.http.ProblemDetail",
//        "Pageable" to "org.springframework.data.domain.Pageable",
//    )
//}
//
//tasks.register("cleanup") {
//    dependsOn("openApiGenerate")
//    doLast {
//        val pom = File("$buildDir/pom.xml")
//        if (pom.exists()) {
//            pom.delete()
//        }
//    }
//}
