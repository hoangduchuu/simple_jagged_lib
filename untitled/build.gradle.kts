plugins {
    java
    application
}

group = "com.developerfect"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.exceptionfactory.jagged:jagged-api:1.0.1-SNAPSHOT")
    implementation("com.exceptionfactory.jagged:jagged-framework:1.0.1-SNAPSHOT")
    implementation("com.exceptionfactory.jagged:jagged-x25519:1.0.1-SNAPSHOT")
}

application {
    mainClass.set("com.developerfect.JaggedDemo")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
} 