plugins {
    id("java")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // JPA
    implementation("javax.persistence:javax.persistence-api:2.2")
    // Sqlite JDBC Driver
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    // ORMLite
    implementation("com.j256.ormlite:ormlite-core:6.1")
    // ORMLite JDBC
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")



}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("basicbike.Main")
}