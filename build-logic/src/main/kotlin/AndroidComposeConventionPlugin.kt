import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val hasLibraryPlugin = plugins.hasPlugin("com.android.library")
            if (!hasLibraryPlugin) {
                throw IllegalStateException("The Android Library or Application plugin must be applied first")
            }

            // Apply the Compose Compiler plugin
            libs.pluginComposeCompiler?.let { composeCompiler ->
                apply(plugin = composeCompiler.get().pluginId)
            }
            
            // Your existing compose configuration if needed
            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
                // Add any other compose-specific configurations here
            }
        }
    }
}