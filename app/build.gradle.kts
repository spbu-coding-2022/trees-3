plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("org.openjfx.javafxplugin") version "0.0.8"
    application
}

javafx {
    version = "11.0.2"
    modules("javafx.controls")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()

}

dependencies {
    implementation("no.tornado:tornadofx:1.7.20")
    implementation(project(":trees"))
}

application {
    // Define the main class for the application.
    mainClass.set("app.AppKt")
}
