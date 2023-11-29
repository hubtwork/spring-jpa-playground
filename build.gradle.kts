plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring.io.dependency)
    alias(libs.plugins.kotlin.allopen) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.springBoot) apply false
    id("build.logic.convention.base")
}

dependencyManagement {
    imports {
        mavenBom(libs.springBoot.dependencies.get().toString())
    }
}