pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "EV_Charging_Stations"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":network")
include(":data")
include(":domain")
include(":model")
include(":common")
include(":datastore")
