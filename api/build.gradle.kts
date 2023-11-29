plugins {
    id("build.logic.convention.springboot.web")
}

dependencies {
    implementation(project(":domain"))
    runtimeOnly(project(":datasource:db-core"))

    implementation(project(":support:logging"))
}