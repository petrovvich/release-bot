group = "it.petrovich"
version = "0.0.1-SNAPSHOT"
description = "release-bot"

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.6.0"
    id("io.micronaut.test-resources") version "3.6.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

val lombokVersion = "1.18.24"

dependencies {
    compileOnly("org.projectlombok", "lombok", lombokVersion)

    implementation("net.logstash.logback", "logstash-logback-encoder", "7.2")
    implementation("com.vladmihalcea", "hibernate-types-52", "2.19.2")
    implementation("org.telegram", "telegrambots", "6.1.0")
    implementation("org.jsoup", "jsoup", "1.15.3")

    implementation("jakarta.annotation", "jakarta.annotation-api")
    implementation("ch.qos.logback", "logback-classic")
    implementation("org.postgresql", "postgresql")

    implementation("io.micronaut.data", "micronaut-data-hibernate-jpa")
    implementation("io.micronaut.liquibase", "micronaut-liquibase")
    implementation("io.micronaut.sql", "micronaut-hibernate-jpa")
    implementation("io.micronaut.sql", "micronaut-jdbc-hikari")
    implementation("io.micronaut", "micronaut-validation")

    annotationProcessor("io.micronaut.data", "micronaut-data-processor")
    annotationProcessor("io.micronaut", "micronaut-http-validation")
    annotationProcessor("io.micronaut", "micronaut-inject-java")
    annotationProcessor("org.projectlombok", "lombok", lombokVersion)

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
    version("3.7.0")
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
