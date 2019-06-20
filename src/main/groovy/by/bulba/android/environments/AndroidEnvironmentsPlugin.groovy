package by.bulba.android.environments

import by.bulba.android.environments.config.ConfigReader
import by.bulba.android.environments.config.PropertyConfigReader
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException

class AndroidEnvironmentsPlugin implements Plugin<Project> {

    private ConfigReader configReader = new PropertyConfigReader()

    @Override
    void apply(Project project) {
        project.extensions
        def ext = project.extensions.create(
                "environments",
                AndroidEnvironmentsExtension
        )
        println()
        println("${ext.configPath}/${ext.configFile}")
        println("buildTypes: ${ext.useBuildTypes}")
        println("productFlavors: ${ext.useProductFlavors}")
        def configFilePattern = project.getRootDir().toString() + "/" +
                ext.configPath + "/%s/" + ext.configFile
        println("configFilePattern: " + configFilePattern)
        try {

            def androidExtension = project.extensions.getByName("android")

            if (ext.useBuildTypes && androidExtension instanceof BaseExtension) {
                processBuildTypes(configFilePattern, androidExtension as BaseExtension)
            }
            println()
            println()
            println("ext.useProductFlavors: " + ext.useProductFlavors + " Ext: " + (androidExtension instanceof AppExtension))
            if (ext.useProductFlavors && androidExtension instanceof AppExtension) {
                processApplicationVariants(configFilePattern, androidExtension as AppExtension)
            }
        } catch (UnknownDomainObjectException udoe) {
            udoe.printStackTrace()
        } finally {
            println("COMPLETE")
        }
//        project.tasks.create("configEnvironments").doFirst {
//        }
    }

    void processBuildTypes(String filePattern, BaseExtension extension) {
        extension.buildTypes.forEach { buildType ->
            def path = String.format(filePattern, buildType.name)
            println("extension.buildType:" + path)
            configReader.propertiesFile = new File(path)
            configReader.configValues.forEach { configValue ->
                println("configValue." +
                        " Key: " + configValue.key +
                        " Type: " + configValue.type +
                        " Value: " + configValue.value)
                buildType.buildConfigField(
                        configValue.type.configString,
                        configValue.key,
                        configValue.value
                )
            }
        }
    }

    void processApplicationVariants(String filePattern, AppExtension extension) {
        extension.applicationVariants.forEach { variant ->
            def path = String.format(filePattern, variant.flavorName)
            println("extension.buildType:" + path)
            configReader.propertiesFile = new File(path)
            configReader.configValues.forEach { configValue ->
                println("configValue." +
                        " Key: " + configValue.key +
                        " Type: " + configValue.type +
                        " Value: " + configValue.value)
                variant.buildConfigField(
                        configValue.type.configString,
                        configValue.key,
                        configValue.value
                )
            }
        }
    }

}
