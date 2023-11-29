plugins {
    id("build.logic.convention.springboot.jpa")
    kotlin("kapt")
}

dependencies {
    compileOnly(project(":domain"))

    implementation(libs.db.h2)
}