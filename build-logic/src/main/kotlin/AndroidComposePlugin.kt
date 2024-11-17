import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val hasLibraryPlugin = plugins.hasPlugin("com.android.library")
            if (!hasLibraryPlugin) {
                throw IllegalStateException("The AndroidLibraryPlugin must be applied first")
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
            }

            // add compose compiler dependencies
            dependencies {
                "debugImplementation"(libs.libComposeUiTooling)
                "implementation"(platform(libs.libComposeBom))
                "implementation"(libs.libComposeUi)
                "implementation"(libs.libComposeUiToolingPreview)
                "implementation"(libs.libComposeMaterial3)
                "implementation"(libs.libComposeLifecycle)
            }
        }
    }
}