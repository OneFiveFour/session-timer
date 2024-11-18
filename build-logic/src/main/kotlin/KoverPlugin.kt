import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class KoverPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            if (this == rootProject) {
                // Apply Kover to the root project
                pluginManager.apply("org.jetbrains.kotlinx.kover")

                // Configure Kover for all projects
                allprojects {
                    pluginManager.apply("org.jetbrains.kotlinx.kover")
                }
            }

            extensions.findByType(KoverProjectExtension::class)?.apply {

            }
        }

    }
}