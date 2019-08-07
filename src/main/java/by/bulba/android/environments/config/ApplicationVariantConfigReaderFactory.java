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
import by.bulba.android.environments.ConfigFormat;
import by.bulba.android.environments.file.ConfigFileProvider;
import by.bulba.android.environments.file.ConfigFileProviderImpl;
import org.gradle.api.Project;

import java.io.File;

/**
 * {@link ApplicationVariantConfigValueReader} factory.
 * Create new instance of application variant config value reader each time.
 */
public class ApplicationVariantConfigReaderFactory {

    /**
     * Create new instance of {@link ApplicationVariantConfigValueReader}
     *
     * @param project   gradle project instance
     * @param extension plugin extension config file
     */
    public ApplicationVariantConfigValueReader create(
            Project project,
            AndroidEnvironmentsExtension extension) {
        File rootConfigPath = new File(project.getRootDir().getPath() + File.pathSeparator + extension.configPath);
        ConfigFormat configFormat = ConfigFormat.parse(extension.format);
        ConfigFileProvider configFileProvider = new ConfigFileProviderImpl(rootConfigPath, configFormat);
        ConfigReaderFactory configReaderFactory = new ConfigReaderFactoryImpl(configFormat);
        return create(configFileProvider, configReaderFactory);
    }

    /**
     * Create new instance of {@link ApplicationVariantConfigValueReader}
     *
     * @param configFileProvider  application variant config file provider
     * @param configReaderFactory configuration file reader instance
     */
    public ApplicationVariantConfigValueReader create(
            ConfigFileProvider configFileProvider,
            ConfigReaderFactory configReaderFactory) {
        return new ApplicationVariantConfigValueReaderImpl(
                configFileProvider, configReaderFactory);
    }

}
