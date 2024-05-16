rootProject.name = "backoffice"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    includeBuild("gradle-plugins")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include("backoffice-api")
include("backoffice-app")
include("backoffice-client")
