import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class KotlinTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Configure test dependencies
            dependencies {
                "testImplementation"(platform(libs.libJunitBom))
                "testImplementation"(libs.libJunitJupiterApi)
                "testImplementation"(libs.libCoroutinesTest)
                "testImplementation"(libs.libMockk)
                "testImplementation"(libs.libTruth)
                "testImplementation"(libs.libTurbine)
            }

            // Configure JUnit platform for test tasks
            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }
}