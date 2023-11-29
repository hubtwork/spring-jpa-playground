import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Provide convention to use JUnit tests.
 */
class JunitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.apply {
                dependencies {
                    add("testRuntimeOnly", libs.findLibrary("junit.platform.launcher").get())
                    add("testImplementation", libs.findBundle("junit").get())
                }
            }
        }
    }
}