/*
 * Copyright (C) 2019. Ilya Pavlovskii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.bulba.android.environments.config;

import by.bulba.android.environments.AndroidEnvironmentsExtension;
import org.gradle.api.Project;
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Simple implementation of {@link ConfigReaderFactory}.
 * */
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

    @VisibleForTesting
    ConfigReader createPropertyConfigReader(File propertiesFile) {
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

    @VisibleForTesting
    String readConfigFilePattern(Project project,
                                 AndroidEnvironmentsExtension ext) {
        return project.getRootDir().getPath() + "/" +
                ext.configPath + "/%s/" + ext.configFile;
    }
}
