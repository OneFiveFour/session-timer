import kotlinx.kover.gradle.plugin.dsl.KoverNames
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class KoverRootPlugin : Plugin<Project> {

    private val koverPluginId = "org.jetbrains.kotlinx.kover"

    override fun apply(target: Project) {

        target.allprojects {
            project.plugins.withId(koverPluginId) {

                // Find the app module
                val appModule = project.rootProject.allprojects
                    .find { it.name == "app" }

                if (appModule != null) {

                    // Find all feature modules with Kover plugin
                    val koverModules = project.rootProject.allprojects
                        .filter { it.plugins.hasPlugin(koverPluginId) }
                        .filter { it != project.rootProject }
                        .filter { it != appModule }

//                    println("Found kover modules: ${koverModules.joinToString(",\n") { it.name }}")

                    // kover configuration in app module
                    val configuration = appModule.configurations.getByName(KoverNames.configurationName)

                    // add kover dependencies in app module
                    koverModules.forEach { koverModule ->
                        addKoverDependency(configuration, koverModule)
                        collectAndApplyFilters(
                            appModule.koverExtension(),
                            koverModule.koverExtension()
                        )
                    }

//                    println(appModule.koverExtension()!!.reports.filters.excludes.classes.get().joinToString(",\n"))
                }
            }
        }
    }

    private fun Project.koverExtension() = this.extensions.findByType(KoverProjectExtension::class.java)

    /**
     * adds the kover dependency to the given configuration:
     * kover(project(":your:feature"))
     */
    private fun Project.addKoverDependency(
        configuration: Configuration,
        koverModule: Project?
    ) {
        checkNotNull(koverModule)

        configuration.dependencies.add(
            project.dependencies.create(koverModule)
        )
    }

    /**
     * adds all kover classes filters of the given featureKover to the appKover.
     */
    private fun collectAndApplyFilters(
        appKover: KoverProjectExtension?,
        featureKover: KoverProjectExtension?
    ) {
        checkNotNull(appKover)
        checkNotNull(featureKover)
        check(appKover != featureKover)

        // Collect filters for source sets, classes, and packages
        val classFilters = featureKover.reports.filters.excludes.classes

        // Apply them to the app module's Kover configuration
        appKover.reports.filters.excludes {
            classes.addAll(classFilters)
        }
    }
}