import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KotlinHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            // Apply the KSP plugin
            libs.pluginKsp?.let { kspPlugin ->
                apply(plugin = kspPlugin.get().pluginId)
            }

            // Configure test dependencies
            dependencies {
                "implementation"(libs.libHiltCore)
                "ksp"(libs.libHiltCompiler)
            }
        }
    }
}