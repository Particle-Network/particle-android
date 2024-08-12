pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://s01.oss.sonatype.org/content/groups/staging/") }
        maven { setUrl("https://s01.oss.sonatype.org/content/repositories/releases/") }
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://maven.google.com/") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }

    }

}

rootProject.name = "particle-android-demo"
include(":app")

