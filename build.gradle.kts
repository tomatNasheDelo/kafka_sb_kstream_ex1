plugins {
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "ru.demo.kafka"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}
