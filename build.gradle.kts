import com.adarshr.gradle.testlogger.theme.ThemeType
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    application
    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.6"
    id("com.adarshr.test-logger") version "4.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "analyzer.code"
version = "1.0-SNAPSHOT"
application.mainClass.set("analyzer.code.App")
repositories.mavenCentral()
tasks.compileJava {
    version = 20
}

tasks.test {
    useJUnitPlatform()
    reports.enabled
    testLogging.exceptionFormat = TestExceptionFormat.FULL
    testlogger {
        setTheme(ThemeType.MOCHA)
        showSummary = true
        showSkipped = true
        showFailed = true
        showPassed = true
    }
}

tasks.jacocoTestReport {
    reports.xml.required = true
}

dependencies {
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    // api
    implementation("com.konghq:unirest-java:4.0.0-RC2")
    implementation("io.javalin:javalin-bundle:5.6.3")
    implementation("io.javalin:javalin-rendering:5.6.2")
    implementation("io.javalin:javalin:5.6.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.16.0")
    implementation("org.jsoup:jsoup:1.17.1")

    // jte
    implementation("gg.jte:jte:3.0.1")
    implementation("gg.jte:jte-watcher:3.0.1")

    // db
    implementation("com.h2database:h2:2.2.222")
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.instancio:instancio-junit:3.3.0")

    // Test deps
    testImplementation("org.assertj:assertj-core:3.25.1")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("com.konghq:unirest-java:3.14.2")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
}
