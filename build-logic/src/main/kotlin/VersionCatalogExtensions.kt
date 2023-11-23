@file:Suppress("UnstableApiUsage")

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

//internal val VersionCatalog.versionKotlinCompiler: String
//    get() = findVersionOrThrow("androidxKotlinCompiler")

private fun VersionCatalog.findLibraryOrThrow(name: String) =
    findLibrary(name)
        .orElseThrow { NoSuchElementException("Library $name not found in version catalog") }

//private fun VersionCatalog.findVersionOrThrow(name: String) =
//    findVersion(name)
//        .orElseThrow { NoSuchElementException("Version $name not found in version catalog") }
//        .requiredVersion
