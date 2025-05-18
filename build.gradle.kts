plugins {
    id("com.android.application") version "8.9.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}
