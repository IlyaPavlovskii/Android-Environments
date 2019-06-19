package by.bulba.android.environments

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidEnvironmentsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions
        def ext = project.extensions.create(
                "environments",
                AndroidEnvironmentsExtension
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
