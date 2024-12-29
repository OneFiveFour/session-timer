import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class KoverConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            // Apply the Kover plugin
            libs.pluginKsp?.let { koverPlugin ->
                apply(plugin = koverPlugin.get().pluginId)
            }

            extensions.findByType(KoverProjectExtension::class.java)?.apply {
                // defines all modules with code coverage
                // st.kotlin.test or st.android.test
                val includedModules = listOf(
                    "common",
                    "database",
                    "defaults",
                    "timer",
                    "usecases",
                    "session-editor",
                    "session-player",
                    "session-overview",
                    "taskgroup-editor",
                )

                merge {
                    allProjects {
                        it.name in includedModules && it.buildFile.exists()
                    }

                    createVariant("merged") {
                        add("debug", optional = true)
                    }
                }

                reports {
                    filters {
                        excludes {
                            annotatedBy("Generated")
                            classes(
                                // general
                                "net.onefivefour.sessiontimer.MainApplication",
                                "net.onefivefour.sessiontimer.MainActivity",
                                "*ComposableSingletons*",
                                "*Hilt_*",
                                "*_HiltModules*",
                                "*BuildConfig",
                                "*_Factory",

                                // database
                                "net.onefivefour.sessiontimer.core.database.*Queries*",
                                "net.onefivefour.sessiontimer.core.database.database.*"
                            )
                            packages(
                                // general
                                "hilt_aggregated_deps",
                                "dagger.hilt.internal.aggregatedroot.codegen",
                                "*.di"
                            )
                        }
                    }
                }
            }
        }
    }
}
