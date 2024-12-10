import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("st.kover")

            // Configure test dependencies
            dependencies {
                "testImplementation"(libs.libJunit)
                "testImplementation"(libs.libCoroutinesTest)
                "testImplementation"(libs.libMockk)
                "testImplementation"(libs.libTruth)
                "testImplementation"(libs.libTurbine)
                "testImplementation"(project(":core:test"))
            }
        }
    }
}