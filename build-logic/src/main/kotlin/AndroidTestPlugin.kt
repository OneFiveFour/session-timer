import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("st.kotlin.test")

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            // Configure test dependencies
            dependencies {
                "testImplementation"(libs.libAndroidXArchCoreTesting)
                "testImplementation"(libs.libHiltAndroidTesting)
                "testImplementation"(libs.libRobolectric)

                "androidTestImplementation"(libs.libComposeTestJUnit)

                "debugImplementation"(libs.libComposeTestManifest)
            }
        }
    }
}