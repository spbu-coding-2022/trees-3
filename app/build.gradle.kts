plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("org.openjfx.javafxplugin") version "0.0.12"
    application
}

javafx {
    version = "18"
    modules("javafx.controls", "javafx.fxml")
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
