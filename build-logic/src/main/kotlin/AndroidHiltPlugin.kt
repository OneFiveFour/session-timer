import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            // Apply the KSP plugin
            libs.pluginKsp?.let { kspPlugin ->
                apply(plugin = kspPlugin.get().pluginId)
            }

            // Configure test dependencies
            dependencies {
                "implementation"(libs.libHiltCore)
                "implementation"(libs.libHiltAndroid)
                "implementation"(libs.libHiltNavigation)
                "ksp"(libs.libHiltAndroidCompiler)
            }
        }
    }
}