group = "it.petrovich"
version = "0.0.1-SNAPSHOT"
description = "release-bot"

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.5.3"
    id("io.micronaut.test-resources") version "3.5.3"
}

repositories {
    mavenLocal()
    mavenCentral()
}

val lombokVersion = "1.18.24"
val micronautCoreVersion = "3.6.3"
val micronautDataVersion = "3.7.3"

dependencies {
    compileOnly("org.projectlombok", "lombok", lombokVersion)

    implementation("ch.qos.logback", "logback-classic")
    implementation("com.vladmihalcea", "hibernate-types-52", "2.19.1")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("org.hibernate.validator", "hibernate-validator", "6.2.4.Final")
    implementation("org.jsoup", "jsoup", "1.15.3")
    implementation("org.postgresql", "postgresql")
    implementation("org.telegram", "telegrambots", "6.1.0")
    implementation("jakarta.persistence", "jakarta.persistence-api", "3.1.0")
    implementation("jakarta.annotation", "jakarta.annotation-api")

    implementation("io.micronaut", "micronaut-validation")
    implementation("io.micronaut.sql", "micronaut-hibernate-jpa")
    implementation("io.micronaut.data", "micronaut-data-hibernate-jpa")
    implementation("io.micronaut.liquibase", "micronaut-liquibase")
    implementation("io.micronaut.sql", "micronaut-jdbc-hikari")

    annotationProcessor("io.micronaut.data", "micronaut-data-processor")
    annotationProcessor("io.micronaut", "micronaut-http-validation")
    annotationProcessor("org.projectlombok", "lombok", lombokVersion)

    testCompileOnly("org.projectlombok", "lombok", lombokVersion)
    testAnnotationProcessor("org.projectlombok", "lombok", lombokVersion)
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

application {
    mainClass.set("it.petrovich.bots.ReleaseApplication")
}

graalvmNative.toolchainDetection.set(false)
micronaut {
    version("3.5.3")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("it.petrovich.bots.*")
    }
}


tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
