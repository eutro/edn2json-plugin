import dev.clojurephant.plugin.clojure.ClojureBuild

plugins {
    java
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.15.0"
    id("dev.clojurephant.clojure") version "0.6.0"
}

group = "io.github.eutro"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        name = "Clojars"
        url = uri("https://repo.clojars.org/")
    }
}

dependencies {
    implementation(gradleApi())
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("org.clojure:clojure:1.10.1")
}

gradlePlugin {
    plugins {
        create("edn2jsonPlugin") {
            id = "eutro.edn2json-plugin"
            implementationClass = "edn2json_plugin.Edn2JsonPlugin"
        }
    }
}

pluginBundle {
    vcsUrl = "https://github.com/eutro/edn2json-plugin"
    website = vcsUrl

    (plugins) {
        "edn2jsonPlugin" {
            displayName = "Edn2Json Plugin"
            tags = listOf("json", "edn", "converter", "transform", "edn2json")
        }
    }
}

clojure {
    builds {
        named<ClojureBuild>("main") {
            aotAll()
            checkNamespaces.set(mutableListOf())
        }
    }
}
