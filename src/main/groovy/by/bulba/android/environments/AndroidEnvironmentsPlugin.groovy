package by.bulba.android.environments

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException

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

                try {
                    def androidExtension = project.extensions.getByName("android")
                    if( androidExtension instanceof BaseExtension) {
                        androidExtension.buildTypes.forEach {
                            println("buildType: ${it.name}")

                        }
                    }
                    if (androidExtension instanceof AppExtension) {
                        androidExtension.applicationVariants.forEach {
                            println("appVariant: ${it.flavorName}")
                        }
                    }
                } catch (UnknownDomainObjectException udoe) {
                    println("android extension not found exception")
                }

            }
        }
    }
}
