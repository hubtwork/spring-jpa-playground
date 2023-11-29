import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Provide convention for Spring Boot.
 *
 * provide `kotlin.plugin.spring`.
 * provide `kotlin-reflect` and spring boot test dependency.
 */
class SpringBootConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.spring")
            }

            extensions.apply {
                dependencies {
                    add("implementation", libs.findLibrary("kotlin.reflect").get())
                    add("testImplementation", libs.findLibrary("springBoot.test").get())
                }
            }
        }
    }
}