import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

/**
 * Provide convention for Spring Boot Jpa.
 *
 * use Spring Boot convention and JUnit convention.
 * provide `kotlin.plugin.jpa` and all-open extension and spring boot data jpa dependency.
 */
class SpringBootJpaConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            with(pluginManager) {
                apply("build.logic.convention.springboot")
                apply("build.logic.convention.junit")
                apply("org.jetbrains.kotlin.plugin.jpa")
            }

            extensions.configure<AllOpenExtension> {
                annotation("javax.persistence.Entity")
                annotation("javax.persistence.Embeddable")
                annotation("javax.persistence.MappedSuperclass")
            }

            dependencies {
                add("implementation", libs.findLibrary("springBoot.starter.data.jpa").get())
            }
        }
    }
}