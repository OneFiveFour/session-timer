plugins {
    id("st.kotlin-library")
    id("app.cash.sqldelight") version "2.0.0"
}

repositories {
    google()
    mavenCentral()
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer")
        }
    }
}