pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("ktorLibs").from("io.ktor:ktor-version-catalog:3.6.0")
    }
    versionCatalogs {
        create("awssdk") {
            from("aws.sdk.kotlin:version-catalog:1.9.17")
        }
    }
}

rootProject.name = "be-server"

