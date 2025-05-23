plugins {
    java
}

group = "com.developerfect"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    // Jagged dependencies
    implementation("com.exceptionfactory.jagged:jagged-api:1.0.1-SNAPSHOT")
    implementation("com.exceptionfactory.jagged:jagged-framework:1.0.1-SNAPSHOT")
    implementation("com.exceptionfactory.jagged:jagged-x25519:1.0.1-SNAPSHOT")
    implementation("com.exceptionfactory.jagged:jagged-scrypt:1.0.1-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}