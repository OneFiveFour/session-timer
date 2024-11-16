import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

class KtlintPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Apply the base ktlint plugin
        project.plugins.apply("org.jlleitschuh.gradle.ktlint")

        // Configure the ktlint extension
        project.extensions.configure<KtlintExtension> {
            version.set("1.0.1")
            verbose.set(true)
            android.set(true)
            outputToConsole.set(true)
            outputColorName.set("RED")
            ignoreFailures.set(false)
            enableExperimentalRules.set(false)

            reporters {
                reporter(ReporterType.PLAIN)
                reporter(ReporterType.CHECKSTYLE)
            }

            filter {
                exclude { element ->
                    element.file.path.contains(
                        project.layout.buildDirectory.dir("generated").get().toString()
                    )
                }
                exclude { element ->
                    element.file.path.contains(
                        project.layout.buildDirectory.dir("generated-sources").get().toString()
                    )
                }
            }
        }
    }
}