plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("jacoco")
    id("org.jetbrains.kotlin.plugin.noarg") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Use the JUnit 5 integration.
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")

    implementation("org.slf4j", "slf4j-simple", "2.0.0")

    implementation("org.neo4j:neo4j-ogm-core:4.0.5")
    runtimeOnly("org.neo4j:neo4j-ogm-bolt-driver:4.0.5")

    implementation("com.google.code.gson:gson:2.10.1")

    // Use JetBrains Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")

    implementation("org.xerial:sqlite-jdbc:3.41.2.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.postgresql:postgresql:42.5.4")
}

tasks.test {
    // Use JUnit Platform for unit tests.
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
        csv.required.set(true)
        csv.outputLocation.set(layout.buildDirectory.file("jacoco/report.csv"))
    }
}

noArg {
    annotation("org.neo4j.ogm.annotation.NodeEntity")
    annotation("org.neo4j.ogm.annotation.RelationshipEntity")
}
