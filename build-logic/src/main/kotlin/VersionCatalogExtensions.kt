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

// Kotlin Test
internal val VersionCatalog.libJunitBom: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("junit-bom")
internal val VersionCatalog.libJunitJupiterApi: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("junit-jupiter-api")
internal val VersionCatalog.libCoroutinesTest: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("coroutines-test")
internal val VersionCatalog.libMockk: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("mockk")

private fun VersionCatalog.findLibraryOrThrow(name: String) =
    findLibrary(name)
        .orElseThrow { NoSuchElementException("Library $name not found in version catalog") }