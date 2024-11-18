import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
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