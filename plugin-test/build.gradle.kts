plugins {
    id("eutro.edn2json-plugin") version "1.0.0"
}

tasks.register<Copy>("copyTask") {
    destinationDir = file("out")
    from("src") {
        filesMatching("**/*.edn") {
            edn2json(this) {
                setPrettyPrinting()
            }
        }
        rename("(.*)\\.edn", "$1.json")
    }
}
