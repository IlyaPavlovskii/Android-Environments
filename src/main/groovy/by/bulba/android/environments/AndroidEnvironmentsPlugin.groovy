package by.bulba.android.environments

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidEnvironmentsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def ext = project.extensions.create(
                "androidEnvironments",
                AndroidEnvironmentsPluginExtension
        )
        project.task("androidEnvTest") {
            doLast {
                println()
                println("${ext.configPath}/${ext.configFile}")
                println("buildTypes: ${ext.useBuildTypes}")
                println("productFlavors: ${ext.useProductFlavors}")
            }
        }
    }
}
