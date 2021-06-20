# Edn2Json Gradle Plugin

I got baited again.

# How to use.

1. Don't.
2. Apply the plugin:
   - Make sure it's in an available repository, e.g. `mavenLocal` where you've published it, or some file repository.

```kotlin
plugins {
    id("eutro.edn2json-plugin") version "<version>"
}
```

3. `edn2json` any files:

- Kotlin

```kotlin
tasks.processResources {
    filesMatching("**/*.edn") {
        edn2json(this)
    }
    rename("(.*)\\.edn", "$1.json")
}
```

- Groovy

```groovy
tasks.processResources {
    filesMatching("**/*.edn") {
        edn2json.invoke(it)
    }
    rename("(.*)\\.edn", "\$1.json")
}
```
