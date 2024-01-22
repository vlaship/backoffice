import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.task

plugins {
    id("java-library")
    id("org.springframework.boot")
}

// task unpack
task("unpack", type = Copy::class) {
    dependsOn("build")
    from(zipTree(tasks.bootJar.get().archiveFile.get()))
    into("build/unpacked")
}
