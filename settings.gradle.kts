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


        maven {
            credentials {
                username = "61127e29fa25fa3e240adbd8"
                password = "Rzx_SYz8nWwR"
            }
            setUrl("https://packages.aliyun.com/maven/repository/2327997-release-rAmT8O/")
        }
        maven {
            credentials {
                username = "61127e29fa25fa3e240adbd8"
                password = "Rzx_SYz8nWwR"
            }
            setUrl("https://packages.aliyun.com/maven/repository/2327997-snapshot-wI6rfM/")
        }
    }

//    enableFeaturePreview("VERSION_CATALOGS")
    versionCatalogs {
        create("libs")
        {
            from(files("libs.versions.toml"))
        }
    }
}

rootProject.name = "particle-android-demo"
include(":app")

