plugins {
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("io.cucumber:cucumber-core:7.19.0")
    implementation("io.cucumber:cucumber-java:7.19.0")
    implementation("com.tngtech.jgiven:jgiven-core:1.3.1")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")
    testImplementation("org.projectlombok:lombok:1.18.34")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.13.0")
    testImplementation("io.cucumber:cucumber-junit:7.19.0")
    testImplementation("com.tngtech.jgiven:jgiven-junit5:1.3.1")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}

tasks.test {
    jvmArgs("-XX:+EnableDynamicAgentLoading")
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}