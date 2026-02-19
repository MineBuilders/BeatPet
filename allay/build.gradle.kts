import groovy.json.JsonSlurper
import groovy.json.JsonOutput

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.allaymc.gradle)
}

allay {
    api = "0.25.0"

    plugin {
        entrance = ".BeatPetPlugin"
        name = "BeatPet"
        authors += "Cdm2883"
        website = "https://github.com/MineBuilders/BeatPet"
    }
}

val includeResourcePack by tasks.registering(Copy::class) {
    val distDir = layout.buildDirectory.dir("resources/main/assets/resource_pack")
    from(rootProject.file("addon/resource_pack"))
    into(distDir)
    @Suppress("UNCHECKED_CAST") doLast {
        val manifestFile = distDir.get().file("manifest.json").asFile
        val manifestData = manifestFile.readText()
            .replace("999.999.999", version.toString())
            .toByteArray()
        val manifest = JsonSlurper().parse(manifestData) as MutableMap<String, Any?>
        val header = manifest["header"] as MutableMap<String, Any?>
        val modules = manifest["modules"] as List<MutableMap<String, Any?>>
        fun Any?.toVersionList() = toString().split(".").map(String::toInt)
        manifest["format_version"] = 2
        header["version"] = header["version"].toVersionList()
        header["min_engine_version"] = header["min_engine_version"].toVersionList()
        modules.forEach { it["version"] = it["version"].toVersionList() }
        manifest.remove("capabilities")
        manifestFile.writeText(JsonOutput.prettyPrint(JsonOutput.toJson(manifest)))
    }
}

tasks.named("processResources") {
    dependsOn(includeResourcePack)
}

afterEvaluate {
    tasks.named<Jar>("shadowJar") {
        archiveBaseName = "beatpet-allay"
        archiveClassifier = ""
    }
}
