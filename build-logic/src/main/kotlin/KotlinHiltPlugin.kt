import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class KotlinHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Configure test dependencies
            dependencies {
                "implementation"(libs.libHiltCore)
                "ksp"(libs.libHiltCompiler)
            }
        }
    }
}