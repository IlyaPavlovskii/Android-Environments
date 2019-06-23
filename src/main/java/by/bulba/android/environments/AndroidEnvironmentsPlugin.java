package by.bulba.android.environments;

import by.bulba.android.environments.config.ConfigReaderFactory;
import by.bulba.android.environments.config.ConfigReaderFactoryImpl;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UnknownDomainObjectException;
import org.jetbrains.annotations.NotNull;

class AndroidEnvironmentsPlugin implements Plugin<Project> {

    private static final String TASK_NAME = "androidBuildConfigEnvironments";

    @Override
    public void apply(@NotNull Project project) {
        project.getExtensions().create(AndroidEnvironmentsExtension.EXTENSIONS_NAME,
                AndroidEnvironmentsExtension.class);
        project.task(TASK_NAME,
                task -> task.mustRunAfter("preBuild").doLast(this::executeTask));
    }

    private void executeTask(Task task) {
        AndroidEnvironmentsExtension ext = task.getProject()
                .getExtensions()
                .findByType(AndroidEnvironmentsExtension.class);

        if (ext == null) {
            ext = new AndroidEnvironmentsExtension();
        }
        ConfigReaderFactory readerFactory = new ConfigReaderFactoryImpl(task.getProject(), ext);

        try {
            Object androidExtension = task.getProject()
                    .getExtensions()
                    .getByName("android");
            if (ext.useBuildTypes && androidExtension instanceof BaseExtension) {
                processBuildTypes(readerFactory, (BaseExtension) androidExtension);
            }
            if (ext.useProductFlavors && androidExtension instanceof AppExtension) {
                processApplicationVariants(readerFactory, (AppExtension) androidExtension);
            }
        } catch (UnknownDomainObjectException udoe) {
            throw new RuntimeException(udoe);
        }
    }

    private void processBuildTypes(ConfigReaderFactory readerFactory, BaseExtension extension) {
        extension.getBuildTypes().forEach(buildType -> readerFactory.create(buildType.getName())
                .getConfigValues()
                .forEach(configValue -> {
                    System.out.println("configValue." +
                            " Key: " + configValue.getKey() +
                            " Type: " + configValue.getType() +
                            " Value: " + configValue.getValue());
                    buildType.buildConfigField(
                            configValue.getType().getConfigString(),
                            configValue.getKey(),
                            configValue.getValue()
                    );
                })
        );
    }

    private void processApplicationVariants(ConfigReaderFactory readerFactory, AppExtension extension) {
        extension.getApplicationVariants().forEach(applicationVariant -> readerFactory
                .create(applicationVariant.getFlavorName())
                .getConfigValues()
                .forEach(configValue -> {
                    System.out.println("configValue." +
                            " Key: " + configValue.getKey() +
                            " Type: " + configValue.getType() +
                            " Value: " + configValue.getValue());
                    applicationVariant.buildConfigField(
                            configValue.getType().getConfigString(),
                            configValue.getKey(),
                            configValue.getValue()
                    );
                })
        );
    }

}
