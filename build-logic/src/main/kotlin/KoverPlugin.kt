import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlinx.kover")

            subprojects {
                pluginManager.apply("org.jetbrains.kotlinx.kover")
            }

            extensions.configure<KoverProjectExtension> {
                reports {
                    total {
                        html {
                            
                        }
                    }
                }

            }
        }
    }
}