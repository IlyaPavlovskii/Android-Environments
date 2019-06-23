package by.bulba.android.environments.config;

import by.bulba.android.environments.AndroidEnvironmentsExtension;
import org.gradle.api.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReaderFactoryImpl implements ConfigReaderFactory {

    private final String configFilePattern;

    public ConfigReaderFactoryImpl(Project project,
                                   AndroidEnvironmentsExtension extension) {
        configFilePattern = readConfigFilePattern(project, extension);
    }

    @Override
    public ConfigReader create(String subConfig) {
        String filePath = String.format(configFilePattern, subConfig);
        File file = new File(filePath);
        return createPropertyConfigReader(file);
    }

    private ConfigReader createPropertyConfigReader(File propertiesFile) {
        Properties properties = new Properties();
        if (propertiesFile.exists()) {
            try {
                properties.load(new FileInputStream(propertiesFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new PropertyConfigReader(properties);
    }

    private String readConfigFilePattern(Project project,
                                         AndroidEnvironmentsExtension ext) {
        return project.getRootDir().toString() + "/" +
                ext.configPath + "/%s/" + ext.configFile;
    }
}
