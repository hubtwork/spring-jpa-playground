plugins {
    id("build.logic.convention.springboot.jpa")
    kotlin("kapt")
}

dependencies {
    compileOnly(project(":domain"))

    runtimeOnly(libs.db.h2)

    implementation(project(":support:logging"))
}