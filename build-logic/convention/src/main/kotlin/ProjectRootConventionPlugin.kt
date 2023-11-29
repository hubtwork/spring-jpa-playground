import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Provide convention for Project overall configuration.
 */
class ProjectRootConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val javaVersion = JavaVersion.VERSION_17.toString()

            subprojects {
                with(pluginManager) {
                    apply("kotlin")
                }
                the<KotlinJvmProjectExtension>().apply {
                    jvmToolchain {
                        languageVersion.set(JavaLanguageVersion.of(javaVersion))
                    }
                    the<JavaPluginExtension>().apply {
                        sourceSets["main"].java.srcDirs("src/main/kotlin")
                    }
                }
            }

            // provide general convention about gradle tasks.
            // provide compiler options about Java & Kotlin.
            // provide Test options.
            allprojects {
                group = "team.hlab.spring-jpa-playground"

                afterEvaluate {
                    tasks.withType(JavaCompile::class.java).configureEach {
                        options.compilerArgs.add("-Xmaxerrs")
                        options.compilerArgs.add("500")
                    }
                    tasks.withType(KotlinCompile::class.java).configureEach {
                        kotlinOptions {
                            jvmTarget = javaVersion

                            freeCompilerArgs =
                                listOf(
                                    "-Xinline-classes",
                                    "-Xjvm-default=all",
                                )
                        }
                    }
                    tasks.withType(Test::class.java) {
                        useJUnitPlatform()
                        testLogging {
                            events.addAll(
                                arrayOf(
                                    TestLogEvent.FAILED,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}