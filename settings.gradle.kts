rootProject.name = "untitled"

// Include jagged modules
include("jagged:jagged-api")
include("jagged:jagged-framework")
include("jagged:jagged-x25519")
include("jagged:jagged-scrypt")

// Set project directories
project(":jagged:jagged-api").projectDir = file("jagged/jagged-api")
project(":jagged:jagged-framework").projectDir = file("jagged/jagged-framework")
project(":jagged:jagged-x25519").projectDir = file("jagged/jagged-x25519")
project(":jagged:jagged-scrypt").projectDir = file("jagged/jagged-scrypt")

