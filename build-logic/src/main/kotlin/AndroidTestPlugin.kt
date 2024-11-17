import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Configure test dependencies
            dependencies {
                "testImplementation"(libs.libAndroidXArchCoreTesting)
            }
        }
    }
}