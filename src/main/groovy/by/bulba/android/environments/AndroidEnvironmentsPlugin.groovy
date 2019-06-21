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
        project.tasks.create("androidConfigEnvironments").doFirst {
            def configFilePattern = project.getRootDir().toString() + "/" +
                    ext.configPath + "/%s/" + ext.configFile
            try {

                def androidExtension = project.extensions.getByName("android")
                if (ext.useBuildTypes && androidExtension instanceof BaseExtension) {
                    processBuildTypes(configFilePattern, androidExtension as BaseExtension)
                }
                if (ext.useProductFlavors && androidExtension instanceof AppExtension) {
                    processApplicationVariants(configFilePattern, androidExtension as AppExtension)
                }
            } catch (UnknownDomainObjectException udoe) {
                udoe.printStackTrace()
            }
        }
    }

    void processBuildTypes(String filePattern, BaseExtension extension) {
        extension.buildTypes.forEach { buildType ->
            def path = String.format(filePattern, buildType.name)
            configReader.propertiesFile = new File(path)
            configReader.configValues.forEach { configValue ->
                buildType.buildConfigField(
                        configValue.type.configString,
                        configValue.key,
                        configValue.value
                )
            }
        }
    }

    void processApplicationVariants(String filePattern, AppExtension extension) {
        extension.applicationVariants.all { variant ->
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
