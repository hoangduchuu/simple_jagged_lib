plugins {
    id("java")
}

group = "com.developerfect"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Jagged core modules for age encryption
    implementation("dev.jagged:jagged-api:1.0.0")
    implementation("dev.jagged:jagged-framework:1.0.0")
    implementation("dev.jagged:jagged-x25519:1.0.0")  // For X25519 key pair encryption
    implementation("dev.jagged:jagged-scrypt:1.0.0")  // For passphrase-based encryption
    
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}