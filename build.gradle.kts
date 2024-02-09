group = "it.petrovich"
version = "0.0.1-SNAPSHOT"
description = "release-bot"

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "3.7.0"
    id("io.micronaut.test-resources") version "3.7.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

val lombokVersion = "1.18.30"
val logstashVersion = "7.4"
val hibernateTypesVersion = "2.21.1"
val telegrambotsVersion = "6.9.7.0"
val jsoupVersion = "1.17.2"
val logbackVersion = "1.4.14"

dependencies {
    compileOnly("org.projectlombok", "lombok", lombokVersion)

    implementation("net.logstash.logback", "logstash-logback-encoder", logstashVersion)
    implementation("com.vladmihalcea", "hibernate-types-52", hibernateTypesVersion)
    implementation("org.telegram", "telegrambots", telegrambotsVersion)
    implementation("org.jsoup", "jsoup", jsoupVersion)

    implementation("ch.qos.logback", "logback-classic", logbackVersion)
    implementation("jakarta.annotation", "jakarta.annotation-api")
    implementation("org.postgresql", "postgresql")

    implementation("io.micronaut.data", "micronaut-data-hibernate-jpa")
    implementation("io.micronaut.liquibase", "micronaut-liquibase")
    implementation("io.micronaut.sql", "micronaut-hibernate-jpa")
    implementation("io.micronaut.sql", "micronaut-jdbc-hikari")
    implementation("io.micronaut", "micronaut-validation")

    annotationProcessor("org.projectlombok", "lombok", lombokVersion)
    annotationProcessor("io.micronaut.data", "micronaut-data-processor")
    annotationProcessor("io.micronaut", "micronaut-http-validation")
    annotationProcessor("io.micronaut", "micronaut-inject-java")

    testAnnotationProcessor("org.projectlombok", "lombok", lombokVersion)
    testCompileOnly("org.projectlombok", "lombok", lombokVersion)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("it.petrovich.bots.ReleaseApplication")
}

graalvmNative.toolchainDetection.set(false)

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("it.petrovich.bots.*")
    }
}

tasks.shadowJar {
    archiveFileName.set("release-bot.jar")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
