plugins {
    id("org.graalvm.buildtools.native")
}

//graalvmNative {
//    binaries {
//        named("main") {
//            imageName.set("app")
//            useFatJar.set(true)
//        }
//    }
//    binaries.all {
//        buildArgs.add("--verbose")
//        resources.autodetect()
//    }
//
//    toolchainDetection.set(true)
//}
graalvmNative {
    binaries {
        named("main") {
//            imageName.set("app")
//            useFatJar.set(true)
            sharedLibrary.set(false)
            mainClass.set("dev.vlaship.backoffice.App")
        }
    }
    binaries.all {
//        buildArgs.add("--verbose")
        resources.autodetect()
    }
//
    toolchainDetection.set(true)
}
