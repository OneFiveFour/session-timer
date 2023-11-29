import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
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
        exclude {
            it.file.path.contains(
                layout.buildDirectory.dir("generated").get().toString()
            )
        }
        exclude {
            it.file.path.contains(
                layout.buildDirectory.dir("generated-sources").get().toString()
            )
        }
    }
}