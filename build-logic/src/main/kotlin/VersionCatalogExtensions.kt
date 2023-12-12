@file:Suppress("UnstableApiUsage")

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

// Android Library
internal val VersionCatalog.versionComposeCompiler: VersionConstraint
    get() = findVersionOrThrow("androidxKotlinCompiler")

// Kotlin Test
internal val VersionCatalog.libJunitBom: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("junit-bom")
internal val VersionCatalog.libJunitJupiterApi: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("junit-jupiter-api")
internal val VersionCatalog.libCoroutinesTest: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("coroutines-test")
internal val VersionCatalog.libMockk: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("mockk")
internal val VersionCatalog.libTruth: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("truth")
internal val VersionCatalog.libAndroidXArchCoreTesting: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-arch-core-testing")

private fun VersionCatalog.findLibraryOrThrow(name: String) =
    findLibrary(name)
        .orElseThrow { NoSuchElementException("Library $name not found in version catalog") }

private fun VersionCatalog.findVersionOrThrow(name: String) =
    findVersion(name)
        .orElseThrow { NoSuchElementException("Version $name not found in version catalog") }