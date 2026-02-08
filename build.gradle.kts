plugins {
    id("fabric-loom") version "1.7-SNAPSHOT"
    kotlin("jvm") version "2.0.0"
}

version = "1.0.0"
group = "com.coinslife"

repositories {
    maven { url = uri("https://maven.terraformersmc.com/releases/") }
    maven { url = uri("https://maven.ladysnake.org/releases") }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.15.11")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.100.0+1.21")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.11.0+kotlin.2.0.0")
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}
