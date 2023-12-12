dependencies {
    val testImplementation by configurations
    testImplementation(platform(libs.libJunitBom))
    testImplementation(libs.libJunitJupiterApi)
    testImplementation(libs.libCoroutinesTest)
    testImplementation(libs.libMockk)
    testImplementation(libs.libTruth)
}

tasks.withType<Test> {
    useJUnitPlatform()
}