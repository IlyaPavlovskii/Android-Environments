package by.bulba.android.environments;

import by.bulba.android.environments.config.ConfigReader;
import by.bulba.android.environments.config.PropertyConfigReader;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UnknownDomainObjectException;
import org.jetbrains.annotations.NotNull;

import java.io.File;

class AndroidEnvironmentsPlugin implements Plugin<Project> {


    private ConfigReader configReader = new PropertyConfigReader();

    @Override
    public void apply(@NotNull Project project) {
        project.getExtensions().create("environments",
                AndroidEnvironmentsExtension.class);
        project.task("androidConfigEnvironments",
                task -> task.doLast(this::executeTask));
    }

    private void executeTask(Task task) {
        AndroidEnvironmentsExtension ext = task.getProject()
                .getExtensions()
                .findByType(AndroidEnvironmentsExtension.class);

        if (ext == null) {
            ext = new AndroidEnvironmentsExtension();
        }
        String configFilePattern = readConfigFilePattern(task, ext);
        System.out.println("ext: " + ext);
        System.out.println("configFilePattern: " + configFilePattern);
        try {
            Object androidExtension = task.getProject()
                    .getExtensions()
                    .getByName("android");
            if (ext.useBuildTypes && androidExtension instanceof BaseExtension) {
                processBuildTypes(configFilePattern, (BaseExtension) androidExtension);
            }
            System.out.println("ext.useProductFlavors: " + ext.useProductFlavors + " Ext: " + (androidExtension instanceof AppExtension));
            if (ext.useProductFlavors && androidExtension instanceof AppExtension) {
                processApplicationVariants(configFilePattern, (AppExtension) androidExtension);
            }
        } catch (UnknownDomainObjectException udoe) {
            udoe.printStackTrace();
        } finally {
            System.out.println("COMPLETE");
        }
    }

    private String readConfigFilePattern(Task task,
                                         AndroidEnvironmentsExtension ext) {
        return task.getProject()
                .getRootDir()
                .toString() + "/" +
                ext.configPath + "/%s/" + ext.configFile;
    }

    private void processBuildTypes(String filePattern, BaseExtension extension) {
        extension.getBuildTypes().forEach(buildType -> {
            String path = String.format(filePattern, buildType.getName());
            System.out.println("extension.buildType:" + path);
            ((PropertyConfigReader) configReader).propertiesFile = new File(path);
            configReader.getConfigValues().forEach(configValue -> {
                System.out.println("configValue." +
                        " Key: " + configValue.getKey() +
                        " Type: " + configValue.getType() +
                        " Value: " + configValue.getValue());
                buildType.buildConfigField(
                        configValue.getType().getConfigString(),
                        configValue.getKey(),
                        configValue.getValue()
                );
            });
        });
    }

    private void processApplicationVariants(String filePattern, AppExtension extension) {
        extension.getApplicationVariants().forEach(applicationVariant -> {
            String path = String.format(filePattern, applicationVariant.getFlavorName());
            System.out.println("extension.buildType:" + path);
            ((PropertyConfigReader) configReader).propertiesFile = new File(path);
            configReader.getConfigValues().forEach(configValue -> {
                System.out.println("configValue." +
                        " Key: " + configValue.getKey() +
                        " Type: " + configValue.getType() +
                        " Value: " + configValue.getValue());
                applicationVariant.buildConfigField(
                        configValue.getType().getConfigString(),
                        configValue.getKey(),
                        configValue.getValue()
                );
            });
        });
    }

}
