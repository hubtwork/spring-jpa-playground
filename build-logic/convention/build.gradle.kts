plugins {
    `kotlin-dsl`
}

group = "com.team-hlab.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.allopen.gradlePlugin)
    compileOnly(libs.springBoot.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("projectRoot") {
            id = "build.logic.convention.base"
            implementationClass = "ProjectRootConventionPlugin"
        }
        register("junit") {
            id = "build.logic.convention.junit"
            implementationClass = "JunitTestConventionPlugin"
        }
        register("springBoot") {
            id = "build.logic.convention.springboot"
            implementationClass = "SpringBootConventionPlugin"
        }
        register("springBootWeb") {
            id = "build.logic.convention.springboot.web"
            implementationClass = "SpringBootWebConventionPlugin"
        }
        register("springBootJpa") {
            id = "build.logic.convention.springboot.jpa"
            implementationClass = "SpringBootJpaConventionPlugin"
        }
    }
}