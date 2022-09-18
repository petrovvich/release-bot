plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

val lombokVersion = "1.18.24"

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("com.vladmihalcea:hibernate-types-52:2.18.0")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("org.hibernate.validator:hibernate-validator:6.2.4.Final")
    implementation("org.jsoup:jsoup:1.15.3")
    implementation("org.liquibase:liquibase-core:4.15.0")
    implementation("org.postgresql:postgresql:42.4.2")
    implementation("org.springframework.boot:spring-boot-configuration-processor:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.3")
    implementation("org.telegram:telegrambots:6.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.3")

    compileOnly("org.projectlombok", "lombok", "$lombokVersion")
    annotationProcessor("org.projectlombok", "lombok", "$lombokVersion")

    testCompileOnly("org.projectlombok", "lombok", "$lombokVersion")
    testAnnotationProcessor("org.projectlombok", "lombok", "$lombokVersion")
}

group = "it.petrovich"
version = "0.0.1-SNAPSHOT"
description = "release-bot"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.jar {
    archiveFileName.set("release-bot.jar")
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
