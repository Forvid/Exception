plugins {
    kotlin("jvm") version "1.9.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("main.MainKt")
}

kotlin {
    jvmToolchain(17)
}
