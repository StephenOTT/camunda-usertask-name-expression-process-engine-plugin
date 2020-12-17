import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val kotlinVersion: String by project
val spekVersion: String by project

plugins {
    id("com.github.johnrengelman.shadow")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    compileOnly(platform("org.camunda.bpm:camunda-bom:7.13.0"))
    compileOnly("org.camunda.bpm:camunda-engine")

}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<ShadowJar>("shadowJar") {
    mergeServiceFiles()

    archiveClassifier.set("server-plugin")

    from(sourceSets.main.get().output)
}