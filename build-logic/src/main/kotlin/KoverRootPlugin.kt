import kotlinx.kover.gradle.plugin.dsl.KoverNames
import org.gradle.api.Plugin
import org.gradle.api.Project

class KoverRootPlugin : Plugin<Project> {

    private val koverPluginId = "org.jetbrains.kotlinx.kover"

    override fun apply(target: Project) {

        target.allprojects {
            project.plugins.withId(koverPluginId) {

                // Find the app module
                val appModule = project.rootProject.allprojects
                    .find { it.name == "app" }

                if (appModule != null) {

                    // Find all modules with Kover plugin
                    val koverModules = project.rootProject.allprojects
                        .filter { it.plugins.hasPlugin(koverPluginId) }
                        .filter { it != project.rootProject }
                        .filter { it != appModule }

                    // kover configuration in app module
                    val configuration =
                        appModule.configurations.getByName(KoverNames.configurationName)

                    // add kover dependencies in app module
                    koverModules.forEach { koverModule ->
                        configuration.dependencies.add(
                            project.dependencies.create(koverModule)
                        )
                    }
                }
            }
        }
    }
}