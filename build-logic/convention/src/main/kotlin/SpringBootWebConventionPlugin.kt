import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.springframework.boot.gradle.tasks.bundling.BootJar

/**
 * Provide convention for Spring Web.
 *
 * use Spring Boot convention and JUnit convention.
 * provide `org.springframework.boot` plugin and bundling options.
 */
class SpringBootWebConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            with(pluginManager) {
                apply("build.logic.convention.springboot")
                apply("build.logic.convention.junit")
                apply("org.springframework.boot")
            }

            tasks.withType(Jar::class.java) {
                enabled = false
            }

            tasks.withType(BootJar::class.java) {
                enabled = true
            }

            dependencies {
                add("implementation", libs.findLibrary("springBoot.starter.web").get())
            }
        }
    }
}