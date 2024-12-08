import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("st.kotlin.test")

            // Configure test dependencies
            dependencies {
                "testImplementation"(libs.libAndroidXArchCoreTesting)
                "testImplementation"(libs.libHiltAndroidTesting)
            }
        }
    }
}