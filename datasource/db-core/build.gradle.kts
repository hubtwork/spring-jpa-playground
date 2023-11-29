plugins {
    id("build.logic.convention.springboot.jpa")
    kotlin("kapt")
}

dependencies {
    compileOnly(project(":domain"))

    runtimeOnly(libs.db.h2)

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.0.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.0.0")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.0.0")
}