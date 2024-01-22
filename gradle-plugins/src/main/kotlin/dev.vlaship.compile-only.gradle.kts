plugins {
    id("java-library")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
