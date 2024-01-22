plugins {
    id("org.graalvm.buildtools.native")
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
