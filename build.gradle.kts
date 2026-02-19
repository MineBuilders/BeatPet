import org.gradle.internal.os.OperatingSystem

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "io.github.minebuilders.beatpet"
description = "A minecraft bedrock plugin that lets you pet any entity!"

version = "1.0.0"

subprojects {
    group = rootProject.group
    description = rootProject.description
    version = rootProject.version
}
