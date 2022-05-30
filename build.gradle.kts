plugins {
    application
    kotlin("jvm") version "1.6.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.ktor:ktor-bom:2.0.2"))
    implementation("io.ktor", "ktor-server-core")
    implementation("io.ktor", "ktor-server-netty")
    implementation("io.ktor", "ktor-server-default-headers")
    implementation("io.ktor", "ktor-server-call-logging")
    runtimeOnly("ch.qos.logback", "logback-classic", "1.3.0-alpha16")
}

application {
    mainClass.set("dev.nycode.ktor.whoami.ApplicationKt")
}
