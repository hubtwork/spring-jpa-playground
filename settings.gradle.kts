
pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":api")
include(":domain")
include(":datasource:db-core")

// library
include(
    "support:logging",
    "support:monitoring",
)

rootProject.name = "spring-jpa-playground"