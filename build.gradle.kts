plugins {
    id("java")
}

group = "org.unito.asd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testImplementation("org.junit.platform:junit-platform-runner:1.11.3")
    implementation("org.jetbrains:annotations:26.0.1")
}

tasks.test {
    useJUnitPlatform()
}